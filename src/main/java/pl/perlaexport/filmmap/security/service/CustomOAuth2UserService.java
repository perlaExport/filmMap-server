package pl.perlaexport.filmmap.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.perlaexport.filmmap.security.user.GoogleUser;
import pl.perlaexport.filmmap.security.user.UserPrincipal;
import pl.perlaexport.filmmap.user.model.AuthType;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private UserRepository userRepository;

    @Autowired
    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        GoogleUser googleUser = new GoogleUser(oAuth2User.getAttributes());
        if (StringUtils.isEmpty(googleUser.getEmail())) {
            throw new RuntimeException("Email not found from OAuth2 provider");
        }
        Optional<UserEntity> userOptional = userRepository.findByEmail(googleUser.getEmail());
        UserEntity user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getAuthType().equals(AuthType.GOOGLE))
                throw new RuntimeException("Use local account to login");
            user = updateExistingUser(user, googleUser);
        } else {
            user = registerNewUser(oAuth2UserRequest, googleUser);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private UserEntity registerNewUser(OAuth2UserRequest oAuth2UserRequest, GoogleUser googleUser) {
        UserEntity user = new UserEntity();
        user.setAuthType(AuthType.GOOGLE);
        user.setName(googleUser.getName());
        user.setEmail(googleUser.getEmail());
        user.setName(googleUser.getName());
        user.setEnabled(true);
        return userRepository.save(user);
    }

    private UserEntity updateExistingUser(UserEntity existingUser, GoogleUser googleUser) {
        existingUser.setName(googleUser.getName());
        return userRepository.save(existingUser);
    }

}
