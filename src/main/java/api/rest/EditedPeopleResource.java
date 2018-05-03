package api.rest;

import api.HTTPException;
import api.rest.utility.ArrayListManipulator;
import api.rest.pojos.PersonChange;
import controllers.security.Auth;
import controllers.security.AuthenticatedUser;
import controllers.stub.StubChangedAppResources;
import data.Person;
import data.Searchable;
import data.SuggestionPerson;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Path("/searchable/suggestions/people")
public class EditedPeopleResource {

    static StubChangedAppResources suggestedResources;

    static {
        suggestedResources= StubChangedAppResources.getInstance();
    }

    @GET
    public Response getEditedPeoples(@CookieParam("sessionToken") Cookie cookie,
                                     @QueryParam("searchString") String searchMatch,
                                     @QueryParam("sort") String sortItem,
                                     @QueryParam("page") Integer page ,
                                     @QueryParam("limit") Integer limit){
        try {
            Auth.authorize(cookie);
            ArrayList<SuggestionPerson> suggPeop = suggestedResources.getAllChangedPeople();
            List<Searchable> searchables = new ArrayList<Searchable>();
            searchables.addAll(suggPeop);
            ArrayListManipulator.searchInNames(searchables,searchMatch);


            return ArrayListManipulator.getPageResponse(searchables,page,limit,sortItem);
        }catch (HTTPException e) {
            e.printStackTrace();
            return e.getHttpResponse();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewPerson(@CookieParam("sessionToken") Cookie cookie, Person person){
        try {
            if(person==null){
                return Response.status(400).build();
            }
            AuthenticatedUser user =Auth.authorize(cookie);
            SuggestionPerson newPerson = new SuggestionPerson(person,-1, Calendar.getInstance().getTime(),user.getUsername());
            suggestedResources.addPerson(newPerson);
            return Response.ok().build();
        }catch (HTTPException e) {
            e.printStackTrace();
            return e.getHttpResponse();
        }
    }

    @Path("/{id}")
    @GET
    public Response getEditedPerson(@CookieParam("sessionToken") Cookie cookie, @PathParam("id") int id){
        try {
            Auth.authorize(cookie);
            return Response.ok(suggestedResources.getPerson(id)).build();
        }catch (HTTPException e) {
            e.printStackTrace();
            return e.getHttpResponse();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEditedPerson(@CookieParam("sessionToken") Cookie cookie, @PathParam("id") int id, PersonChange personChange){
        try {
            AuthenticatedUser user = Auth.authorize(cookie);
            SuggestionPerson newPerson = new SuggestionPerson(personChange.getNewPerson(),id, Calendar.getInstance().getTime(),user.getUsername());
            suggestedResources.updatePerson(newPerson,personChange.getOldPerson());
            return Response.ok().build();
        }catch (HTTPException e) {
            e.printStackTrace();
            return e.getHttpResponse();
        }
    }

    @Path("/{id}")
    @DELETE
    public Response deleteEditedPerson(@CookieParam("sessionToken") Cookie cookie, @PathParam("id") int id){
        try {
            Auth.authorizeAdmin(cookie);
            suggestedResources.deletePersonChange(id);
            return Response.ok().build();
        }catch (HTTPException e) {
            e.printStackTrace();
            return e.getHttpResponse();
        }
    }

    @Path("/{id}/approval")
    @POST
    public Response approveEditedPerson(@CookieParam("sessionToken") Cookie cookie, @PathParam("id") int id){
        try {
            Auth.authorizeAdmin(cookie);
            suggestedResources.approvePerson(id);
            return Response.ok().build();
        }catch (HTTPException e) {
            e.printStackTrace();
            return e.getHttpResponse();
        }
    }

}
