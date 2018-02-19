package websider;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import data.Note;
import data.NoteDao;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

@Path("noter")
public class Noter {
    private static NoteDao noteDao = new NoteDao();

    @GET
    public String getNoter() throws IOException {
        //Initialize Mustache renderer
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("noter.mustache");
        //Set some data
        HashMap<String, Object> mustacheData = new HashMap<String, Object>();
        mustacheData.put("noter",noteDao.getNoter());
        //render template with data
        StringWriter writer = new StringWriter();
        m.execute(writer, mustacheData).flush();
        return writer.toString();
    }

    @POST
    public String postNote(@FormParam("note") String note, @FormParam("delete") Integer no) throws IOException {
        if(note!=null) {
            noteDao.saveNote(note);
        }
        if(no!=null){
            noteDao.deleteNote(no);
        }
        return getNoter();
    }
}