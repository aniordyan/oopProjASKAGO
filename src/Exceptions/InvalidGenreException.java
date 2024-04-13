package Exceptions;

public class InvalidGenreException extends Exception{
    public InvalidGenreException(){
        super();
    }

    public InvalidGenreException(String message){
        super(message);
    }
}
