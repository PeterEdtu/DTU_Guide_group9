package api.rest;


import api.HTTPException;
import org.json.JSONObject;
import controllers.security.Auth;
import controllers.security.AuthenticatedUser;
import api.rest.pojos.LoginPojo;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/security")
public class Security {

    @Path("session")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSession(@CookieParam("sessionToken") Cookie cookie) {
        String jsonString = null;
        try{
            AuthenticatedUser user = Auth.authorize(cookie);
            jsonString = new JSONObject()
                    .put("validSession","true")
                    .put("username",user.getUsername())
                    .put("isAdmin",user.isAdmin())
                    .put("exp",user.getExpDate())
                    .toString();

            return Response.ok(jsonString).build();
        }catch (Exception e){
            jsonString = new JSONObject()
                    .put("validSession","false")
                    .toString();
            return Response.ok(jsonString).build();
        }
    }

    @Path("session")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginPojo loginInfo) {


        try {
            String jwt = Auth.authenticate(loginInfo.getUsername(), loginInfo.getPassword());

            NewCookie sampleCookie = new NewCookie("sessionToken", "");
            NewCookie sessionCookie =new NewCookie("sessionToken", jwt, "/REST", sampleCookie.getDomain(), sampleCookie.getVersion(), null, sampleCookie.getMaxAge(), null, false, false);
            AuthenticatedUser au=Auth.authorize(sessionCookie);
            String jsonString = new JSONObject()
                    .put("validSession","true")
                    .put("username",au.getUsername())
                    .put("isAdmin",au.isAdmin())
                    .put("exp",au.getExpDate())
                    .toString();
            System.err.println("Jsonstring is: " +jsonString);

            return Response.ok(jsonString).cookie(sessionCookie).build();

        } catch (HTTPException e) {
            return e.getHttpResponse();
        }
    }

    @DELETE
    @Path("session")
    public Response logout(@CookieParam("sessionToken") Cookie cookie) {

        if (cookie != null) {
            NewCookie newCookie = new NewCookie("sessionToken", "", "/REST", cookie.getDomain(), cookie.getVersion(), null, 0, new Date(0), false, false);

            return Response.ok("OK").cookie(newCookie).build();
        }
        return Response.ok("OK - No Session").build();
    }

}
