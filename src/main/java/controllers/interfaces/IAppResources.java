package controllers.interfaces;

import controllers.exceptions.DataAccessException;
import controllers.exceptions.NotFoundException;
import data.Searchable;

import java.util.ArrayList;

public interface IAppResources {

    ArrayList<Searchable> search(String searchText) throws DataAccessException, NotFoundException;



}
