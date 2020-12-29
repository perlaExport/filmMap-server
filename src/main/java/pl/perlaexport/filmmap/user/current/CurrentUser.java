package pl.perlaexport.filmmap.user.current;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import pl.perlaexport.filmmap.security.exception.UserNotAuthenticatedException;
import pl.perlaexport.filmmap.security.exception.UserNotFoundException;
import pl.perlaexport.filmmap.security.jwt.TokenAuthenticationFilter;
import pl.perlaexport.filmmap.security.jwt.TokenProvider;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

@Component
public class CurrentUser {
    private static TokenAuthenticationFilter tokenAuthenticationFilter;
    private static TokenProvider tokenProvider;
    private static UserRepository userRepository;

    @Autowired
    public CurrentUser(TokenAuthenticationFilter tokenAuthenticationFilter, TokenProvider tokenProvider,
                       UserRepository userRepository) {
        CurrentUser.tokenAuthenticationFilter = tokenAuthenticationFilter;
        CurrentUser.tokenProvider = tokenProvider;
        CurrentUser.userRepository = userRepository;
    }

    public static UserEntity get(HttpServletRequest request) {
        String token = tokenAuthenticationFilter.getJwtFromRequest(request);
        return  userRepository.findById(tokenProvider.getUserIdFromToken(token)).orElseThrow( () ->
                new UserNotAuthenticatedException(tokenProvider.getUserIdFromToken(token))
        );
    }
}
