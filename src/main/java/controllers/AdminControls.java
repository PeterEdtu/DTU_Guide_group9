package controllers;

import controllers.exceptions.DataAccessException;
import controllers.exceptions.NotFoundException;
import controllers.interfaces.IAdminControls;
import database.connector.Connector;
import database.connector.DummyConnector;

import java.util.ArrayList;
import java.util.List;


public class AdminControls implements IAdminControls {

 //   DummyConnector connector = new DummyConnector();

    Connector connector = new Connector();

    private static AdminControls controller = null;

    private AdminControls() {
    }


    public static synchronized AdminControls getInstance() {
        if (controller == null) {
            controller = new AdminControls();
        }

        return controller;
    }


    @Override
    public boolean isAdmin(String username) throws DataAccessException{
        return connector.getAdmins().contains(username);
    }

    @Override
    public ArrayList<String> getAdminNames() throws DataAccessException{
        return connector.getAdmins();
    }

    @Override
    public void addAdmin(String adminName) throws DataAccessException {
        connector.createAdmin(adminName);
    }

    @Override
    public void removeAdmin(String adminName) throws DataAccessException{
        if(isAdmin(adminName)){
            connector.deleteAdmin(adminName);
        }
    }
}
