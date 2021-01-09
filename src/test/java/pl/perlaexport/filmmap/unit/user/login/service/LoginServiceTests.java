package pl.perlaexport.filmmap.unit.user.login.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.perlaexport.filmmap.security.jwt.TokenProvider;
import pl.perlaexport.filmmap.user.login.dto.LoginDto;
import pl.perlaexport.filmmap.user.login.response.ResponseLogin;
import pl.perlaexport.filmmap.user.login.response.ResponseLoginFailure;
import pl.perlaexport.filmmap.user.login.service.LoginServiceImpl;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class LoginServiceTests {
    @Mock
    TokenProvider tokenProvider;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    LoginServiceImpl loginService;

    UserEntity user;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        loginService = new LoginServiceImpl(userRepository,passwordEncoder,authenticationManager,tokenProvider);
        user = new UserEntity();
        user.setId(1L);
        user.setName("user");
        user.setPassword("password");
    }
    @Test
    void login() {
        //given
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("email");
        loginDto.setPassword("password");
        String token = "TOKEN";
        Authentication authentication = mock(Authentication.class);
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(),anyString())).willReturn(true);
        //when
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenProvider.createToken(any(Authentication.class))).thenReturn(token);
        ResponseLogin response = loginService.login(loginDto);

        //then
        assertNotNull(response);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider, times(1)).createToken(any(Authentication.class));
    }

    @Test
    void loginWithBadMail() {
        //given
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("email");
        loginDto.setPassword("password");
        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(passwordEncoder.matches(anyString(),anyString())).willReturn(true);
        //when
        ResponseLogin response = loginService.login(loginDto);

        //then
        assertNotNull(response);
    }

    @Test
    void loginWithBadPassword() {
        //given
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("email");
        loginDto.setPassword("password");
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(),anyString())).willReturn(false);
        //when
        ResponseLogin response = loginService.login(loginDto);

        //then
        assertNotNull(response);
    }
}
