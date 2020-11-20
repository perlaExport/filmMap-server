package pl.perlaexport.filmmap.user.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RegistrationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailIsAlreadyTakenException.class)
    public String getEmailIsAlreadyTakenException(EmailIsAlreadyTakenException ex){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordNotMatchesException.class)
    public String getPasswordNotMatchesException(PasswordNotMatchesException ex){
        return ex.getMessage();
    }
}
