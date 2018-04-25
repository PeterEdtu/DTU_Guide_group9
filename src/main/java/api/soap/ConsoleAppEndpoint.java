package api.soap;

import javax.jws.WebService;
import java.util.ArrayList;


@WebService(endpointInterface = "api.soap.ConsoleAppInterface")
public class ConsoleAppEndpoint implements ConsoleAppInterface {

    @Override
    public void addAdmin(String name) {

    }

    @Override
    public void removeAdmin(String name) {

    }

    @Override
    public ArrayList<String> getAdminList() {
        return null;
    }
}
