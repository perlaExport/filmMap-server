package pl.perlaexport.filmmap.movie.exception;

public class NotMoviesInDatabaseException extends RuntimeException{
    public NotMoviesInDatabaseException(){
        super("Not movies unrated by user found in database");
    }
}
