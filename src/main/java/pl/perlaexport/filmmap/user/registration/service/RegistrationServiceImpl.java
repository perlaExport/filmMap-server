package pl.perlaexport.filmmap.user.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.perlaexport.filmmap.user.model.AuthType;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.registration.dto.RegistrationDto;
import pl.perlaexport.filmmap.user.registration.exception.EmailIsAlreadyTakenException;
import pl.perlaexport.filmmap.user.registration.exception.PasswordNotMatchesException;
import pl.perlaexport.filmmap.user.repository.UserRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity register(RegistrationDto registrationDto) {
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent())
            throw new EmailIsAlreadyTakenException(registrationDto.getEmail());
        if (!registrationDto.getPassword().equals(registrationDto.getMatchingPassword()))
            throw new PasswordNotMatchesException();
        UserEntity user = UserEntity.builder().email(registrationDto.getEmail()).
                password(passwordEncoder.encode(registrationDto.getPassword())).name(registrationDto.getName()).
                authType(AuthType.LOCAL).enabled(true).build();
        return userRepository.save(user);
    }
}
