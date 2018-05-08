package controllers.exceptions;

import api.HTTPException;

import javax.ws.rs.core.Response;

public class AlreadyExistException extends HTTPException {

    public AlreadyExistException(String message){
        super(message);
    }

    public String toString(){
        return "AlreadyExistException : " + super.getMessage();
    }

    @Override
    public Response getHttpResponse() {
        return Response.status(409).build();
    }
}
