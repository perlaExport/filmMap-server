package pl.perlaexport.filmmap.recommendation.exception;

public class TooLessRatingsException extends RuntimeException{
    public TooLessRatingsException(String movieId){
        super("Cannot recommend " + movieId + " in cause of too less ratings");
    }
}
