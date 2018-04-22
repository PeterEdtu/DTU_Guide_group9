package controllers.exceptions;

public class NotFoundException extends Exception {

    public NotFoundException(String message){
        super(message);
    }

    public String toString(){
        return "NotFoundException : " + super.getMessage();
    }
}
