package api.soap;

import controllers.AdminControls;
import controllers.exceptions.DataAccessException;
import controllers.stub.StubAdminControls;

import javax.jws.WebService;
import java.util.ArrayList;



@WebService(endpointInterface = "api.soap.ConsoleAppInterface")
public class ConsoleAppEndpoint implements ConsoleAppInterface {

    private AdminControls adminControl= AdminControls.getInstance();

    public ConsoleAppEndpoint(){
        System.out.println("Starting soap interface");
    }

    @Override
    public boolean addAdmin(String name) {
        try {
            adminControl.addAdmin(name);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean removeAdmin(String name) {
        try {
            adminControl.removeAdmin(name);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<String> getAdminList() {
        try {
            return adminControl.getAdminNames();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
