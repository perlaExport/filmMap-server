package pl.perlaexport.filmmap.recommendation.exception;

public class MovieAlreadyRatedException extends RuntimeException{
    public MovieAlreadyRatedException(String movieId){
        super("Movie with id: " + movieId + " is already rated by user");
    }
}
