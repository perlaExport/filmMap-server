package pl.perlaexport.filmmap.rating.exception;

public class BadRatingRangeException extends RuntimeException {
    public BadRatingRangeException(Integer rating){
        super("Rating: " + rating + " is out of range 1 - 5");
    }
}
