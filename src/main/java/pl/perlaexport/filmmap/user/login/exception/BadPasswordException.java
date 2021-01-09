package pl.perlaexport.filmmap.user.login.exception;

public class BadPasswordException extends RuntimeException{
    public BadPasswordException(String email){
        super(email);
    }
}
