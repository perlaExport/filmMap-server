package pl.perlaexport.filmmap.recommendation.exception;

public class NotEnoughDataException extends  RuntimeException{
    public NotEnoughDataException(){
        super("Not enough data to recommend any movies for a user");
    }
}
