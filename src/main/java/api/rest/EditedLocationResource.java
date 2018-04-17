package api.rest;

import api.rest.pojos.LocationChange;
import data.Location;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/searchable/edits/locations")
public class EditedLocationResource {

    @GET
    public Response getEditedLocations(){
        return Response.ok("location-edits-get").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewLocation(Location location){
        return Response.ok("location-edits-post"+location.name).build();
    }

    @Path("/{id}")
    @GET
    public Response getEditedLocation(@PathParam("id") int id){
        return Response.ok("location-edits-id-get").build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)

    public Response updateEditedLocation(@PathParam("id") int id, LocationChange changedLocation){

        return Response.ok("location-edits-id-post"+changedLocation.newLocation.name+changedLocation.oldLocation.name).build();
    }

    @Path("/{id}")
    @DELETE
    public Response deleteEditedLocation(@PathParam("id") int id){
        return Response.ok("location-edits-id-delete").build();
    }

    @Path("/{id}/approval")
    @POST
    public Response approveEditedLocation(@PathParam("id") int id){
        return Response.ok("location-edits-id-approval-post").build();
    }
}
