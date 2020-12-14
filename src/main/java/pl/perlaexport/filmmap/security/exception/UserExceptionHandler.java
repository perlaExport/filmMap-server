package pl.perlaexport.filmmap.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.perlaexport.filmmap.common.exception.ExceptionResponse;

@RestControllerAdvice
public class UserExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ExceptionResponse getUserNotFoundException(UserNotFoundException ex){
        return new ExceptionResponse(ex.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LocalAccountException.class)
    public ExceptionResponse getLocalAccountException(LocalAccountException ex){
        return new ExceptionResponse(ex.getMessage());
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmailNotFoundException.class)
    public ExceptionResponse getEmailNotFoundException(EmailNotFoundException ex){
        return new ExceptionResponse(ex.getMessage());
    }
}
