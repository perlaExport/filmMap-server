package pl.perlaexport.filmmap.security.exception;

public class LocalAccountException extends RuntimeException{
    public LocalAccountException(){
        super("Use Local account to login");
    }
}
