package pl.perlaexport.filmmap.movie.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String movieId){
        super("Movie with id: " + movieId + " not found!");
    }
}
