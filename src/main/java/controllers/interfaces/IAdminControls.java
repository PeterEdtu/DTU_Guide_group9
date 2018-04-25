package controllers.interfaces;

import controllers.exceptions.DataAccessException;
import controllers.exceptions.NotFoundException;

import java.util.ArrayList;

public interface IAdminControls {

    boolean isAdmin(String username) throws DataAccessException, NotFoundException;

    ArrayList<String> getAdminNames() throws DataAccessException, NotFoundException;

    void addAdmin(String adminName) throws  DataAccessException;

    void removeAdmin(String adminName) throws DataAccessException, NotFoundException;


}
