package controllers.exceptions;

import api.HTTPException;

import javax.ws.rs.core.Response;

public class NotFoundException extends HTTPException {

    public NotFoundException(String message){
        super(message);
    }

    public String toString(){
        return "NotFoundException : " + super.getMessage();
    }

    @Override
    public Response getHttpResponse() {
        return Response.status(404).build();
    }
}
