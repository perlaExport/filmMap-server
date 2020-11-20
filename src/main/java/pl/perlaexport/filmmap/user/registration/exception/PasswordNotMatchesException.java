package pl.perlaexport.filmmap.user.registration.exception;

public class PasswordNotMatchesException extends RuntimeException {
    public PasswordNotMatchesException(){
        super("Passwords not matches!");
    }
}
