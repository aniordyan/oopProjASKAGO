package am.aua.exceptions;

public class ParamNotFoundException extends Exception {

    public ParamNotFoundException(){
        super("Incomplete information about audio file");
    }

    public ParamNotFoundException(String message){
        super(message);
    }

    //throw new ParamNotFoundException("The parameter was not found.");
}
