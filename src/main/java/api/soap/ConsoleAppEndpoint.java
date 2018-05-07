package api.soap;

import controllers.stub.StubAdminControls;

import javax.jws.WebService;
import java.util.ArrayList;



@WebService(endpointInterface = "api.soap.ConsoleAppInterface")
public class ConsoleAppEndpoint implements ConsoleAppInterface {

    private StubAdminControls adminControl=StubAdminControls.getInstance();

    public ConsoleAppEndpoint(){
        System.out.println("Starting soap interface");
    }

    @Override
    public boolean addAdmin(String name) {
        adminControl.addAdmin(name);
        return true;
    }

    @Override
    public boolean removeAdmin(String name) {
        adminControl.removeAdmin(name);
        return true;
    }

    @Override
    public ArrayList<String> getAdminList() {
        return adminControl.getAdminNames();
    }
}
