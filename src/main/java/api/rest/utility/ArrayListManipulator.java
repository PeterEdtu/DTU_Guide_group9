package api.rest.utility;

import data.Searchable;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArrayListManipulator {

    public static List<Searchable> searchInNames(List<Searchable> list,String search){
        List<Searchable> outList=new ArrayList<Searchable>();
        for (Searchable s: list) {
            if(s.searchName().contains(search)){
                outList.add(s);
            }
        }
        return outList;
    }

    public static Response getPageResponse(List<Searchable> searchables, Integer page, Integer limit, String sortItem){
        int totalItems= searchables.size();
        if(limit==null){
            limit=Integer.MAX_VALUE;
        }
        if(page==null){
            page=1;
        }

        if(sortItem!=null) {
            if (sortItem.equals("name")) {
                searchables.sort(Comparator.comparing(o -> o.searchName()));
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
