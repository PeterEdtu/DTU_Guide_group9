package api.soap;

import javax.jws.WebService;
import java.util.ArrayList;


@WebService(endpointInterface = "api.soap.ConsoleAppInterface")
public class ConsoleAppEndpoint implements ConsoleAppInterface {

    @Override
    public boolean addAdmin(String name) {
        System.out.println("It worked!");
        return true;
    }

    @Override
    public boolean removeAdmin(String name) {
        return true;
    }

    @Override
    public ArrayList<String> getAdminList() {
        return null;
    }
}
