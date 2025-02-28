package pl.perlaexport.filmmap.user.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.perlaexport.filmmap.security.jwt.TokenProvider;
import pl.perlaexport.filmmap.user.login.dto.LoginDto;
import pl.perlaexport.filmmap.user.login.exception.BadEmailException;
import pl.perlaexport.filmmap.user.login.exception.BadPasswordException;
import pl.perlaexport.filmmap.user.login.exception.NotActivatedAccountException;
import pl.perlaexport.filmmap.user.login.response.ResponseLogin;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public ResponseLogin login(LoginDto loginDto) {
        UserEntity user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(BadEmailException::new);
        if (!user.isEnabled())
            throw new NotActivatedAccountException(user.getEmail());
        if (!passwordEncoder.matches(loginDto.getPassword(),user.getPassword()))
            throw new BadPasswordException(user.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return new ResponseLogin(token,user);
    }
}
