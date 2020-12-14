package pl.perlaexport.filmmap.user.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.perlaexport.filmmap.common.exception.ExceptionResponse;

@RestControllerAdvice
public class RegistrationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailIsAlreadyTakenException.class)
    public ExceptionResponse getEmailIsAlreadyTakenException(EmailIsAlreadyTakenException ex){
        return new ExceptionResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordNotMatchesException.class)
    public ExceptionResponse getPasswordNotMatchesException(PasswordNotMatchesException ex){
        return new ExceptionResponse(ex.getMessage());
    }
}
