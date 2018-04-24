package api.rest;

import api.rest.pojos.PersonChange;
import data.Person;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/searchable/suggestions/people")
public class EditedPeopleResource {

    @GET
    public Response getEditedPeoples(){
        return Response.ok("People-edits-get").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewPerson(Person person){
        return Response.ok("Person-edits-post").build();
    }

    @Path("/{id}")
    @GET
    public Response getEditedPerson(@PathParam("id") int id){
        return Response.ok("Person-edits-id-get").build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEditedPerson(@PathParam("id") int id, PersonChange personChange){
        return Response.ok("Person-edits-id-post").build();
    }

    @Path("/{id}")
    @DELETE
    public Response deleteEditedPerson(@PathParam("id") int id){
        return Response.ok("Person-edits-id-delete").build();
    }

    @Path("/{id}/approval")
    @POST
    public Response approveEditedPerson(@PathParam("id") int id){
        return Response.ok("Person-edits-id-approval-post").build();
    }

}
