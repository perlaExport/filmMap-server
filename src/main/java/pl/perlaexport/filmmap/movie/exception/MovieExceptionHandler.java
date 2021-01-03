package pl.perlaexport.filmmap.movie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.perlaexport.filmmap.common.exception.ExceptionResponse;

@RestControllerAdvice
public class MovieExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MovieNotFoundException.class)
    public ExceptionResponse getMovieNotFoundException(MovieNotFoundException ex){
        return new ExceptionResponse(ex.getMessage());
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(MovieAlreadyExistsException.class)
    public ExceptionResponse getMovieAlreadyExistsException(MovieAlreadyExistsException ex){
        return new ExceptionResponse(ex.getMessage());
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotMoviesInDatabaseException.class)
    public ExceptionResponse getNotMoviesInDatabaseException(NotMoviesInDatabaseException ex){
        return new ExceptionResponse(ex.getMessage());
    }
}

