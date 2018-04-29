package api;

import javax.ws.rs.core.Response;

public abstract class HTTPException extends Exception{

    public HTTPException(){

    }

    public HTTPException(String message){
        super(message);
    }

    public Response getHttpResponse(){
        return Response.status(500).build();
    }
}
