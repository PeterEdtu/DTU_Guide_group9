package controllers.exceptions;

import api.HTTPException;

import javax.ws.rs.core.Response;

public class DataAccessException extends HTTPException {

    public DataAccessException(String message){
        super(message);
    }

    public String toString(){
        return "DataAccessException : " + super.getMessage();
    }

    @Override
    public Response getHttpResponse() {
        return Response.status(500).build();
    }
}
