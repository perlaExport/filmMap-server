package pl.perlaexport.filmmap.user.mail.token.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String cause){
        super("Token " + cause);
    }
}
