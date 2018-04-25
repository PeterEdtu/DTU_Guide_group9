package controllers.stub;

import controllers.interfaces.IChangedAppResources;
import controllers.exceptions.DataAccessException;
import controllers.exceptions.ItemOverwriteException;
import controllers.exceptions.NotFoundException;
import data.Location;
import data.Person;
import data.SuggestionLocation;
import data.SuggestionPerson;
import database.connector.DummyConnector;


import java.util.ArrayList;
import java.util.HashMap;

public class StubChangedAppResources implements IChangedAppResources {

    int id=0;
    HashMap<Integer,SuggestionPerson> people;
    HashMap<Integer, SuggestionLocation> location;


    private static StubChangedAppResources controller = null;

    private StubChangedAppResources(){
        people = new HashMap<>();
        location = new HashMap<>();
    }

    public static synchronized StubChangedAppResources getInstance(){
        if(controller == null){
            controller = new StubChangedAppResources();
        }

        return controller;
    }

    @Override
    public void addLocation(SuggestionLocation loc) throws DataAccessException {

        location.put(id++,loc);
    }

    @Override
    public void updateLocation(SuggestionLocation newLoc, SuggestionLocation previousLocation) throws ItemOverwriteException, DataAccessException {
        if(previousLocation==null){
            throw new ItemOverwriteException();
        }

        location.put(newLoc.getSuggestionID(),newLoc);

    }

    @Override
    public ArrayList<SuggestionLocation> getAllChangedLocations() throws DataAccessException, NotFoundException {
        return (ArrayList)location.values();
    }

    @Override
    public SuggestionLocation getLocation(int id) throws DataAccessException, NotFoundException {
        return location.get(id);
    }


    @Override
    public void deleteLocationChange(int id) throws DataAccessException, NotFoundException {
        location.remove(id);
    }

    @Override
    public void approveLocation(int id) throws DataAccessException, NotFoundException {
        location.remove(id);
    }

    @Override
    public void addPerson(SuggestionPerson person) throws DataAccessException {

        people.put(id++,person);
    }

    @Override
    public void updatePerson(SuggestionPerson person, SuggestionPerson previousPerson) throws DataAccessException, ItemOverwriteException {

        people.put(person.getSuggestionID(),person);
    }

    @Override
    public ArrayList<SuggestionPerson> getAllChangedPeople() {
        return (ArrayList)people.values();
    }

    @Override
    public SuggestionPerson getPerson(int id) throws DataAccessException, NotFoundException {
        return people.get(id);
    }

    @Override
    public void deletePersonChange(int id) {
        people.remove(id);
    }

    @Override
    public void approvePerson(int id) {
        people.remove(id);
    }
}