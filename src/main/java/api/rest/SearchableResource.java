package api.rest;

import api.rest.utility.ArrayListManipulator;
import controllers.AppResources;
import controllers.exceptions.DataAccessException;
import controllers.exceptions.NotFoundException;
import controllers.stub.StubAppResources;
import data.Location;
import data.Person;
import data.Searchable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/searchable")
public class SearchableResource {
    static AppResources res;

    static {
        res = AppResources.getInstance();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSearchables(@QueryParam("searchString") String searchMatch,
                                      @QueryParam("sort") String sortItem,
                                      @QueryParam("page") Integer page ,
                                      @QueryParam("limit") Integer limit,
                                      @QueryParam("type") String type)
    {
        List<Searchable> searchables = null;
        if(searchMatch==null){
            searchMatch="";
        }
        try {
            searchables = res.search(searchMatch);
        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
            return e.getHttpResponse();
        }
        if(limit==null){
            limit=Integer.MAX_VALUE;
        }
        if(page==null){
            page=1;
        }



        if(type!=null&&!type.isEmpty()){
            searchables = getFilteredList(searchables, type);
        }



        return ArrayListManipulator.getPageResponse(searchables,page,limit,sortItem);
    }


    private ArrayList<Searchable> getFilteredList(List<Searchable> list,String type){
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
