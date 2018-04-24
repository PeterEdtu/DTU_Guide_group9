package api.rest;

import data.Location;
import data.Person;
import data.Searchable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/searchable")
public class SearchableResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSearchables(@QueryParam("searchString") String searchMatch){


        //TODO: Replace with search call.
        ArrayList<Searchable> searchables= new ArrayList<Searchable>();
        Location loc = new Location();
        loc.setName("x1.10") ;
        searchables.add(loc);
        Location loc1 = new Location();
        loc1.setName("x1.12");
        searchables.add(loc1);
        Person pers = new Person();
        pers.setName("Arvid");
        pers.setName("x1.12");
        searchables.add(pers);

        return Response.ok(searchables).build();
    }
}
