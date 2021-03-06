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
import java.util.Calendar;
import java.util.HashMap;

public class StubChangedAppResources implements IChangedAppResources {

    int id=0;
    HashMap<Integer,SuggestionPerson> people;
    HashMap<Integer, SuggestionLocation> location;


    private static StubChangedAppResources controller = null;

    private StubChangedAppResources(){
        people = new HashMap<>();
        location = new HashMap<>();
        Location[] locArr= new Location[4];
        locArr[0]= new Location("a1.44","This is a room",1,"NONE",72.2,30.4,null);
        locArr[1]= new Location("r1.24","This is a room",2,"NONE",72.2,30.4,null);
        locArr[2]= new Location("u1.11","This is a room",1,"NONE",72.2,30.4,null);
        locArr[3]= new Location("v1.20","This is a room",1,"NONE",72.2,30.4,null);

        for(id=0; id<4;id++){
            location.put(id,new SuggestionLocation(locArr[id],id,Calendar.getInstance().getTime(),"s144265"));
        }
    }


    public static synchronized StubChangedAppResources getInstance(){
        if(controller == null){
            controller = new StubChangedAppResources();
        }

        return controller;
    }

    @Override
    public void addLocation(SuggestionLocation loc) throws DataAccessException {
        loc.setSuggestionID(id);
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
        return new ArrayList<SuggestionLocation>(location.values());
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
        person.setSuggestionID(id);
        people.put(id++,person);
    }

    @Override
    public void updatePerson(SuggestionPerson person, SuggestionPerson previousPerson) throws DataAccessException, ItemOverwriteException {

        people.put(person.getSuggestionID(),person);
    }

    @Override
    public ArrayList<SuggestionPerson> getAllChangedPeople() {

        return new ArrayList<SuggestionPerson>(people.values());
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