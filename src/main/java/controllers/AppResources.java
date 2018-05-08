package controllers;

import controllers.exceptions.DataAccessException;
import controllers.exceptions.NotFoundException;
import controllers.interfaces.IAppResources;
import data.Location;
import data.Person;
import data.Searchable;
import database.connector.Connector;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;

public class AppResources implements IAppResources {

    Connector connector = new Connector();

    private static AppResources controller = null;

    static char[] charsBackList;

    private AppResources() {
        charsBackList = new char[]{'{', '}','#','@','$','(',')','[',']'};
    }


    public static synchronized AppResources getInstance() {
        if (controller == null) {
            controller = new AppResources();
        }

        return controller;
    }


    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Searchable> search(String searchText) throws DataAccessException, NotFoundException {
        ArrayList<Searchable> result = new ArrayList<>();

        HashMap<String, Location> locations = connector.getLocations(searchText);
        HashMap<Integer, Person> persons = connector.getPeople(searchText);

        if(locations == null || persons == null){
            throw new DataAccessException("Null pointer at Searchable List");
        }else if(locations.isEmpty() && persons.isEmpty()){
            //TODO
            return new ArrayList<>();
        }else{
            result.addAll(locations.values());
            result.addAll(persons.values());

            return result;
        }
    }
}
