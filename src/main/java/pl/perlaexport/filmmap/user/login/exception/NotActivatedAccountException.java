package pl.perlaexport.filmmap.user.login.exception;

public class NotActivatedAccountException extends RuntimeException{
    public NotActivatedAccountException(String email){
        super("Account with email: " + email + " is not activated");
    }
}
