package pl.perlaexport.filmmap.movie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MovieExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MovieNotFoundException.class)
    public String getMovieNotFoundException(MovieNotFoundException ex){
        return ex.getMessage();
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(MovieAlreadyExistsException.class)
    public String getMovieAlreadyExistsException(MovieAlreadyExistsException ex){
        return ex.getMessage();
    }
}

