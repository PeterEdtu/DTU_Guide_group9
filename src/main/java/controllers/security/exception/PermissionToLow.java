package controllers.security.exception;

import api.HTTPException;

import javax.ws.rs.core.Response;

public class PermissionToLow extends HTTPException {
    @Override
    public Response getHttpResponse() {
        return Response.status(403).build();
    }
}
