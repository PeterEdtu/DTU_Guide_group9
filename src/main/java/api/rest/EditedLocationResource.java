package api.rest;

import api.rest.pojos.LocationChange;
import data.Location;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/searchable/suggestions/locations")
public class EditedLocationResource {

    @GET
    public Response getEditedLocations(){
        return Response.ok("location-edits-get").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewLocation(Location location){
        return Response.ok("location-edits-post"+location.getName()).build();
    }

    @Path("/{name}")
    @GET
    public Response getEditedLocation(@PathParam("name") String name){
        return Response.ok("location-edits-id-get").build();
    }

    @Path("/{name}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)

    public Response updateEditedLocation(@PathParam("name") int name, LocationChange changedLocation){

        return Response.ok("location-edits-id-post"+changedLocation.newLocation.getName()+changedLocation.oldLocation.getName()).build();
    }

    @Path("/{name}")
    @DELETE
    public Response deleteEditedLocation(@PathParam("name") int name){
        return Response.ok("location-edits-id-delete").build();
    }

    @Path("/{name}/approval")
    @POST
    public Response approveEditedLocation(@PathParam("name") int name){
        return Response.ok("location-edits-id-approval-post").build();
    }
}
