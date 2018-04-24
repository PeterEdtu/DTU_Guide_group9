package api.rest;

import controllers.stub.StubAppResources;
import data.Location;
import data.Person;
import data.Searchable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/searchable")
public class SearchableResource {
    static StubAppResources res;

    static {
        res = StubAppResources.getInstance();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSearchables(@QueryParam("searchString") String searchMatch, @QueryParam("type") String type){
        ArrayList<Searchable> searchables =res.search(searchMatch);

        if(type!=null&&!type.isEmpty()){
            searchables = getFilteredList(searchables, type);
        }

        return Response.ok(searchables).build();
    }


    private ArrayList<Searchable> getFilteredList(ArrayList<Searchable> list,String type){
        ArrayList<Searchable> filteredList  = new ArrayList<>();

        for(Searchable s:list) {
            if (type.equals("person")) {
                if (s instanceof Person) {
                    filteredList.add(s);
                }
            } else if (type.equals("location")) {
                if (s instanceof Location) {
                    filteredList.add(s);
                }
            }
            else{
                throw new BadRequestException(type + "is not a valid type");
            }
        }
        return filteredList;
    }
}
