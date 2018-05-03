package controllers.security.exception;

import api.HTTPException;

import javax.ws.rs.core.Response;

public class InvalidToken extends HTTPException {
    @Override
    public Response getHttpResponse() {
        return Response.status(401).build();
    }
}
