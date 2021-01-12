package pl.perlaexport.filmmap.recommendation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.perlaexport.filmmap.common.exception.ExceptionResponse;

@RestControllerAdvice
public class RecommendationExceptionHandler {
    @ExceptionHandler(TooLessRatingsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse getTooLessRatingsException(TooLessRatingsException ex){
        return new ExceptionResponse(ex.getMessage());
    }
    @ExceptionHandler(MovieAlreadyRatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse getMovieAlreadyRatedException(MovieAlreadyRatedException ex){
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(NotEnoughDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse getNotEnoughDataException(TooLessRatingsException ex){
        return new ExceptionResponse(ex.getMessage());
    }
}
