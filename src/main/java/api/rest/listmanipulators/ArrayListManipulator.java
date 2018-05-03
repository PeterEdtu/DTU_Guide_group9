package api.rest.listmanipulators;

import data.Searchable;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.util.Comparator;
import java.util.List;

public class ArrayListManipulator {

    public static List<Searchable> searchInNames(List<Searchable> list,String search){
        return list;
    }

    public static Response getPageResponse(List<Searchable> searchables, Integer page, Integer limit, String sortItem){
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

}
