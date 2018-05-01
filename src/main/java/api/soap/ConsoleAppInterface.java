package api.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

@WebService()
public interface ConsoleAppInterface {

    @WebMethod
    boolean addAdmin(String name);
    @WebMethod
    boolean removeAdmin(String name);
    @WebMethod
    ArrayList<String> getAdminList();
}
