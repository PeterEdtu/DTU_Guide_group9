package controllers.exceptions;

public class DataAccessException extends Exception {

    public DataAccessException(String message){
        super(message);
    }

    public String toString(){
        return "DataAccessException : " + super.getMessage();
    }
}
