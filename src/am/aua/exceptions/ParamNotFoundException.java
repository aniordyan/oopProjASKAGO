package am.aua.exceptions;

public class ParamNotFoundException extends Exception {

    public ParamNotFoundException(String message){
        super(message);
    }

    //throw new ParamNotFoundException("The parameter was not found.");
}
