package controllers.stub;

import api.interfaces.IAdminControls;

import java.util.ArrayList;


public class StubAdminControls implements IAdminControls {

ArrayList<String> admins = new ArrayList<String>();

    private static StubAdminControls controller = null;

    private StubAdminControls(){
    }

    public static synchronized StubAdminControls getInstance(){
        if(controller == null){
            controller = new StubAdminControls();
        }

        return controller;
    }

    @Override
    public boolean isAdmin(String username) {
        return false;
    }

    @Override
    public ArrayList<String> getAdminNames() {
        return null;
    }

    @Override
    public void addAdmin(String adminName) {

    }

    @Override
    public void removeAdmin(String adminName) {

    }
}
