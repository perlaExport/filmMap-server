package pl.perlaexport.filmmap.user.login.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.perlaexport.filmmap.user.login.response.ResponseLoginFailure;

@RestControllerAdvice
public class LoginExceptionHandler {
    @ExceptionHandler(BadEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseLoginFailure getBadEmailException(BadEmailException ex){
        return new ResponseLoginFailure();
    }
    @ExceptionHandler(BadPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseLoginFailure getBadPasswordException(BadPasswordException ex){
        return new ResponseLoginFailure(ex.getMessage());
    }
}
