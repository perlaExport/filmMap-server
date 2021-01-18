package pl.perlaexport.filmmap.user.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.perlaexport.filmmap.security.exception.EmailNotFoundException;
import pl.perlaexport.filmmap.security.exception.UserNotFoundException;
import pl.perlaexport.filmmap.user.mail.MailForwarder;
import pl.perlaexport.filmmap.user.mail.token.type.TokenType;
import pl.perlaexport.filmmap.user.model.AuthType;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.registration.dto.RegistrationDto;
import pl.perlaexport.filmmap.user.registration.exception.EmailIsAlreadyTakenException;
import pl.perlaexport.filmmap.user.mail.token.exception.InvalidTokenException;
import pl.perlaexport.filmmap.user.registration.exception.PasswordNotMatchesException;
import pl.perlaexport.filmmap.user.mail.token.model.VerificationToken;
import pl.perlaexport.filmmap.user.mail.token.repository.TokenRepository;
import pl.perlaexport.filmmap.user.repository.UserRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final MailForwarder mailForwarder;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                   MailForwarder mailForwarder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailForwarder = mailForwarder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void register(RegistrationDto registrationDto) {
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent())
            throw new EmailIsAlreadyTakenException(registrationDto.getEmail());
        if (!registrationDto.getPassword().equals(registrationDto.getMatchingPassword()))
            throw new PasswordNotMatchesException();
        UserEntity user = UserEntity.builder().email(registrationDto.getEmail()).
                password(passwordEncoder.encode(registrationDto.getPassword())).name(registrationDto.getName()).
                authType(AuthType.LOCAL).enabled(false).build();
        user = userRepository.save(user);
        mailForwarder.sendRegistrationMail(createVerificationToken(user).getToken(), user);
    }

    @Override
    public UserEntity confirmRegistration(Long userId, String token) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        VerificationToken t = tokenRepository.findByUserAndTokenAndTokenType(user, token, TokenType.REGISTER)
                .orElseThrow(() -> new InvalidTokenException("not found"));
        if (t.getExpiryDate().before(new Date()))
            throw new InvalidTokenException("expired");
        user.setEnabled(true);
        tokenRepository.delete(t);
        return userRepository.save(user);
    }

    @Override
    public void resendVerificationToken(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException(email)
        );
        Optional<VerificationToken> t = tokenRepository.findByUserAndTokenType(user,TokenType.REGISTER);
        VerificationToken token;
        if (t.isPresent()) {
            token = t.get();
            token.updateToken();
            tokenRepository.save(token);
        } else
            token = createVerificationToken(user);
        mailForwarder.sendRegistrationMail(token.getToken(), user);
    }

    private VerificationToken createVerificationToken(UserEntity user) {
        VerificationToken token = new VerificationToken(user,TokenType.REGISTER);
        return tokenRepository.save(token);
    }
}
