package pl.perlaexport.filmmap.security.exception;

public class UserNotAuthenticatedException extends RuntimeException{
    public UserNotAuthenticatedException(long id){
        super("User with id : " + id + " is not authenticated");
    }
}
