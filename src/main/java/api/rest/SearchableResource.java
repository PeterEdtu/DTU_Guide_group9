package api.rest;

import controllers.stub.StubAppResources;
import data.Location;
import data.Person;
import data.Searchable;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Path("/searchable")
public class SearchableResource {
    static StubAppResources res;

    static {
        res = StubAppResources.getInstance();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSearchables(@QueryParam("searchString") String searchMatch,
                                      @QueryParam("sort") String sortItem,
                                      @QueryParam("page") Integer page ,
                                      @QueryParam("limit") Integer limit,
                                      @QueryParam("type") String type)
    {
        List<Searchable> searchables =res.search(searchMatch);
        if(limit==null){
            limit=Integer.MAX_VALUE;
        }
        if(page==null){
            page=1;
        }

        if(type!=null&&!type.isEmpty()){
            searchables = getFilteredList(searchables, type);
        }
        int totalItems= searchables.size();

        if(sortItem!=null) {
            if (sortItem.equals("name")) {
                searchables.sort(Comparator.comparing(o -> o.getSearchName()));
            }
        }

        if(page!=null&&limit!=null&&page>0&&limit>0){
            try {
                searchables = searchables.subList((page - 1) * limit, Math.min(limit * page,searchables.size()));
            }catch (RuntimeException e){
                return Response.status(416).build();
            }
        }
        String jsonOut = new JSONObject()
                .put("numPages",(int) Math.ceil(totalItems/(double)limit))
                .put("totalItems",totalItems)
                .put("itemsPerPage",limit)
                .put("pageNumber",page)
                .put("data",searchables)
                .toString();

        return Response.ok(jsonOut).build();
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
