package pl.perlaexport.filmmap.rating.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.perlaexport.filmmap.common.exception.ExceptionResponse;

@RestControllerAdvice
public class RatingExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRatingRangeException.class)
    public ExceptionResponse getBadRatingException(BadRatingRangeException ex) {
        return new ExceptionResponse(ex.getMessage());
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RatingNotFoundException.class)
    public ExceptionResponse getRatingNotFoundException(RatingNotFoundException ex){
        return new ExceptionResponse(ex.getMessage());
    }
}
