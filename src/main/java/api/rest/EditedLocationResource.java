package api.rest;

import api.HTTPException;
import api.rest.pojos.LocationChange;
import controllers.exceptions.NotFoundException;
import controllers.security.Auth;
import controllers.security.AuthenticatedUser;
import controllers.stub.StubChangedAppResources;
import data.Location;
import data.SuggestionLocation;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;

@Path("/searchable/suggestions/locations")
public class EditedLocationResource {

    static StubChangedAppResources suggestedResources;

    static {
        suggestedResources= StubChangedAppResources.getInstance();
    }

    @GET
    public Response getEditedLocations(@CookieParam("sessionToken") Cookie cookie){
        try {
            Auth.authorize(cookie);
            ArrayList<SuggestionLocation> suggLoc = suggestedResources.getAllChangedLocations();
            return Response.ok(suggLoc).build();
        }catch (NotFoundException e) {
            return Response.status(204).build();
        }catch (HTTPException e) {
            e.printStackTrace();
            return e.getHttpResponse();
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewLocation(@CookieParam("sessionToken") Cookie cookie,Location location){
        try {
            if(location==null){
                return Response.status(400).build();
            }
            AuthenticatedUser user =Auth.authorize(cookie);
            SuggestionLocation newLocation = new SuggestionLocation(location,-1, Calendar.getInstance().getTime(),user.getUsername());
            suggestedResources.addLocation(newLocation);
            return Response.ok().build();
        }catch (HTTPException e) {
            e.printStackTrace();
            return e.getHttpResponse();
        }
    }

    @Path("/{id}")
    @GET
    public Response getEditedLocation(@CookieParam("sessionToken") Cookie cookie, @PathParam("id") int id){
        try {
            Auth.authorize(cookie);
            return Response.ok(suggestedResources.getLocation(id)).build();
        }catch (HTTPException e) {
            e.printStackTrace();
            return e.getHttpResponse();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEditedLocation(@CookieParam("sessionToken") Cookie cookie, @PathParam("id") int id, LocationChange changedLocation){
        try {
            AuthenticatedUser user = Auth.authorize(cookie);
            SuggestionLocation newLocation = new SuggestionLocation(changedLocation.newLocation,id, Calendar.getInstance().getTime(),user.getUsername());
            suggestedResources.updateLocation(newLocation,changedLocation.oldLocation);
            return Response.ok().build();
        }catch (HTTPException e) {
            e.printStackTrace();
            return e.getHttpResponse();
        }
    }

    @Path("/{id}")
    @DELETE
    public Response deleteEditedLocation(@CookieParam("sessionToken") Cookie cookie, @PathParam("id") int id){
        try {
            Auth.authorizeAdmin(cookie);
            suggestedResources.deleteLocationChange(id);
            return Response.ok().build();
        }catch (HTTPException e) {
            e.printStackTrace();
            return e.getHttpResponse();
        }
    }

    @Path("/{id}/approval")
    @POST
    public Response approveEditedLocation(@CookieParam("sessionToken") Cookie cookie, @PathParam("id") int id){
        try {
            Auth.authorizeAdmin(cookie);
            suggestedResources.approveLocation(id);
            return Response.ok().build();
        }catch (HTTPException e) {
            e.printStackTrace();
            return e.getHttpResponse();
        }
    }
}
