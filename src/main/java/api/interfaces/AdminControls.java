package api.interfaces;

import java.util.ArrayList;

public interface AdminControls {

    boolean isAdmin(String username);

    ArrayList<String> getAdminNames();

    void addAdmin(String adminName);

    void removeAdmin(String adminName);


}
