package am.aua.demo.exceptions;

public class InvalidGenreException extends Exception{
    public InvalidGenreException(){
        super("The provided genre is invalid");
    }

    public InvalidGenreException(String message){
        super(message);
    }
}
