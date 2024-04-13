package am.aua.exceptions;

public class SongNotFoundException extends Exception{
    public SongNotFoundException(){
        super();
    }
    public SongNotFoundException(String message){
        super(message);
    }
}
