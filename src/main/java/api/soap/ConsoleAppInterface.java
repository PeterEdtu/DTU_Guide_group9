package api.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;

@WebService
public interface ConsoleAppInterface {

    @WebMethod
    void addAdmin(String name);
    @WebMethod
    void removeAdmin(String name);
    @WebMethod
    ArrayList<String> getAdminList();
}
