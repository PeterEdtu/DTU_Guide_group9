package api.rest;


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
    public Response getSession(@CookieParam("sessionToken") Cookie cookie) {
        String jsonString = null;
        try{
            AuthenticatedUser user = Auth.authorize(cookie);
            jsonString = new JSONObject()
                    .put("validSession","true")
                    .put("username",user.getUsername())
                    .put("role","admin")
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
    public Response login(LoginPojo loginInfo) {


        try {
            String jwt = Auth.authenticate(loginInfo.getUsername(), loginInfo.getPassword());

            NewCookie sampleCookie = new NewCookie("sessionToken", "");
            NewCookie sessionCookie =new NewCookie("sessionToken", jwt, "/REST", sampleCookie.getDomain(), sampleCookie.getVersion(), null, sampleCookie.getMaxAge(), null, false, false);
            String jsonString = new JSONObject()
                    .put("validSession","true")
                    .toString();
            System.err.println("Jsonstring is: " +jsonString);

            return Response.ok(jsonString).cookie(sessionCookie).build();

        } catch (Exception e) {
            return Response.status(401).build();
        }
    }

    @DELETE
    @Path("session")
    public Response logout(@CookieParam("sessionToken") Cookie cookie) {

        if (cookie != null) {
            NewCookie newCookie = new NewCookie("sessionToken", "", "/REST", cookie.getDomain(), cookie.getVersion(), null, 0, new Date(0), false, true);

            return Response.ok("OK").cookie(newCookie).build();
        }
        return Response.ok("OK - No Session").build();
    }

}
