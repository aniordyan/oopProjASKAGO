package am.aua.exceptions;

public class SongNotFoundException extends Exception{
    public SongNotFoundException(){
        super("No such song");
    }
    public SongNotFoundException(String message){
        super(message);
    }
}
