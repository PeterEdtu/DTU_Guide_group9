package controllers.stub;

import controllers.interfaces.IAdminControls;

import java.util.ArrayList;


public class StubAdminControls implements IAdminControls {

    ArrayList<String> admins = new ArrayList<String>();

    private static StubAdminControls controller = null;

    private StubAdminControls(){
        admins.add("s144265");
    }

    public static synchronized StubAdminControls getInstance(){
        if(controller == null){
            controller = new StubAdminControls();
        }

        return controller;
    }

    @Override
    public boolean isAdmin(String username) {
        if(admins.contains(username)){
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<String> getAdminNames() {
        return admins;
    }

    @Override
    public void addAdmin(String adminName) {
        if(!admins.contains(adminName)){
            admins.add(adminName);
        }
    }

    @Override
    public void removeAdmin(String adminName) {
        admins.remove(adminName);
    }
}
