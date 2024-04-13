package Exceptions;

public class TitleNotFoundException extends Exception {

    public TitleNotFoundException(String message) {
        super(message);
    }
    // throw new TitleNotFoundException("The title was not found.");
}