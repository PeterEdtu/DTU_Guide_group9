package controllers;

import api.interfaces.IAdminControls;

import java.util.ArrayList;


public class AdminControls implements IAdminControls {

ArrayList<String> admins = new ArrayList<String>();


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
