package pl.perlaexport.filmmap.user.mail.token.repository;

import org.springframework.data.repository.CrudRepository;
import pl.perlaexport.filmmap.user.mail.token.type.TokenType;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.mail.token.model.VerificationToken;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByUserAndTokenAndTokenType(UserEntity user, String token, TokenType tokenType);
    Optional<VerificationToken> findByUserAndTokenType(UserEntity user, TokenType tokenType);
}
