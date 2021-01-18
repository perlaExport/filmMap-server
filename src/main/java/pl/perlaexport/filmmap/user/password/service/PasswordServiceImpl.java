package pl.perlaexport.filmmap.user.password.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.perlaexport.filmmap.security.exception.EmailNotFoundException;
import pl.perlaexport.filmmap.security.exception.UserNotFoundException;
import pl.perlaexport.filmmap.user.mail.MailForwarder;
import pl.perlaexport.filmmap.user.mail.token.model.VerificationToken;
import pl.perlaexport.filmmap.user.mail.token.repository.TokenRepository;
import pl.perlaexport.filmmap.user.mail.token.type.TokenType;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.password.dto.ResetPasswordDto;
import pl.perlaexport.filmmap.user.mail.token.exception.InvalidTokenException;
import pl.perlaexport.filmmap.user.registration.exception.PasswordNotMatchesException;
import pl.perlaexport.filmmap.user.repository.UserRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class PasswordServiceImpl implements PasswordService{

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final MailForwarder mailForwarder;
    private final PasswordEncoder encoder;

    @Autowired
    public PasswordServiceImpl(UserRepository userRepository, TokenRepository tokenRepository,
                               MailForwarder mailForwarder, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.mailForwarder = mailForwarder;
        this.encoder = encoder;
    }

    @Override
    public void changePasswordRequest(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException(email)
        );
        mailForwarder.sendPasswordMail(createVerificationToken(user).getToken(), user);
    }

    @Override
    public void changePassword(Long userId, String token, ResetPasswordDto resetPasswordDto) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        VerificationToken t = tokenRepository.findByUserAndTokenAndTokenType(user, token, TokenType.PASSWORD)
                .orElseThrow(() -> new InvalidTokenException("not found"));
        if (t.getExpiryDate().before(new Date()))
            throw new InvalidTokenException("expired");
        if (!resetPasswordDto.getMatchingPassword().equals(resetPasswordDto.getPassword()))
            throw new PasswordNotMatchesException();
        user.setPassword(encoder.encode(resetPasswordDto.getPassword()));
        tokenRepository.delete(t);
        userRepository.save(user);
    }

    @Override
    public void resendChangePasswordToken(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException(email)
        );
        Optional<VerificationToken> t = tokenRepository.findByUserAndTokenType(user,TokenType.PASSWORD);
        VerificationToken token;
        if (t.isPresent()) {
            token = t.get();
            token.updateToken();
            tokenRepository.save(token);
        } else
            token = createVerificationToken(user);
        mailForwarder.sendPasswordMail(token.getToken(), user);
    }

    private VerificationToken createVerificationToken(UserEntity user) {
        VerificationToken token = new VerificationToken(user, TokenType.PASSWORD);
        return tokenRepository.save(token);
    }
}
