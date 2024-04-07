package Exceptions;

public class CarNotAvailableException extends Exception{

    public CarNotAvailableException(){
        super();
    }

    public CarNotAvailableException(String message){
        super(message);
    }
}
