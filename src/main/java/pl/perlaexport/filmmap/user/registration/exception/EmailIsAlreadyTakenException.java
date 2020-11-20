package pl.perlaexport.filmmap.user.registration.exception;

public class EmailIsAlreadyTakenException extends RuntimeException {
    public EmailIsAlreadyTakenException(String email){
        super(email + " is already taken");
    }
}
