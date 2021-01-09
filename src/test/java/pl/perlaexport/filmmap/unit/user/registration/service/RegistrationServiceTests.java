package pl.perlaexport.filmmap.unit.user.registration.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.registration.dto.RegistrationDto;
import pl.perlaexport.filmmap.user.registration.exception.EmailIsAlreadyTakenException;
import pl.perlaexport.filmmap.user.registration.exception.PasswordNotMatchesException;
import pl.perlaexport.filmmap.user.registration.service.RegistrationServiceImpl;
import pl.perlaexport.filmmap.user.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTests {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    RegistrationServiceImpl service;

    UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setName("user");
    }

    @Test
    void registerNewUserAccount() {
        //given
        RegistrationDto accountDto = new RegistrationDto("email@email.com","user","password","password");

        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        //when
        UserEntity result = service.register(accountDto);

        //then
        then(userRepository).should().findByEmail(anyString());
        then(passwordEncoder).should().encode(any());
        then(userRepository).should().save(any(UserEntity.class));
    }

    @Test
    void registerNewUserAccountWithExistingEmail() {
        //given
        RegistrationDto accountDto = new RegistrationDto("email@email.com","user","password","password");

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));

        //when
        Throwable result = assertThrows(EmailIsAlreadyTakenException.class, () -> service.register(accountDto));

        //then
        assertEquals(new EmailIsAlreadyTakenException(accountDto.getEmail()).getMessage(), result.getMessage());
        then(userRepository).should().findByEmail(anyString());
        then(userRepository).shouldHaveNoMoreInteractions();
        then(passwordEncoder).shouldHaveNoInteractions();
    }

    @Test
    void registerNewUserAccountWithNotMatchingPasswords() {
        //given
        RegistrationDto accountDto = new RegistrationDto("email@email.com","user","password","passwordy");

        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());

        //when
        Throwable result = assertThrows(PasswordNotMatchesException.class, () -> service.register(accountDto));

        //then
        assertEquals(new PasswordNotMatchesException().getMessage(), result.getMessage());
        then(userRepository).shouldHaveNoMoreInteractions();
        then(passwordEncoder).shouldHaveNoInteractions();
    }
}
