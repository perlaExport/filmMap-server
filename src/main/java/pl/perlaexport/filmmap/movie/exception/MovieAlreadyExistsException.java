package pl.perlaexport.filmmap.movie.exception;

public class MovieAlreadyExistsException extends RuntimeException {
    public MovieAlreadyExistsException(String movieId){
        super("Movie with id: " + movieId + " already exists");
    }
}
