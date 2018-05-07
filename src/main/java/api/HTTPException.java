package api;

import javax.ws.rs.core.Response;

public abstract class HTTPException extends Exception{

    protected HTTPException(){

    }

    protected HTTPException(String message){
        super(message);
    }

    public Response getHttpResponse(){
        return Response.status(500).build();
    }
}
