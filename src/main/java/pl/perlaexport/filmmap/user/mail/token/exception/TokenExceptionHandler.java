package pl.perlaexport.filmmap.user.mail.token.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.perlaexport.filmmap.common.exception.ExceptionResponse;

@RestControllerAdvice
public class TokenExceptionHandler {
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse getInvalidTokenException(InvalidTokenException ex){
        return new ExceptionResponse(ex.getMessage());
    }
}
