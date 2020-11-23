package pl.perlaexport.filmmap.rating.exception;

public class RatingNotFoundException extends RuntimeException {
    public RatingNotFoundException(String movieId, String email){
        super("User with email: " + email + " not rated movie with id: " + movieId);
    }
}
