package controllers.stub;

import controllers.interfaces.IChangedAppResources;
import controllers.exceptions.DataAccessException;
import controllers.exceptions.ItemOverwriteException;
import controllers.exceptions.NotFoundException;
import data.Location;
import data.Person;
import database.DummyConnector;

import java.util.ArrayList;
import java.util.HashMap;

public class StubChangedAppResources implements IChangedAppResources {

    ArrayList<Person> people;
    HashMap<String, Location> location;


    private static StubChangedAppResources controller = null;

    private StubChangedAppResources(){
        people = new ArrayList<>();
        location = new HashMap<>();
    }

    public static synchronized StubChangedAppResources getInstance(){
        if(controller == null){
            controller = new StubChangedAppResources();
        }

        return controller;
    }

    @Override
    public void addLocation(Location loc) throws DataAccessException {
        location.put(loc.getName(),loc);
    }

    @Override
    public void updateLocation(Location newLoc, Location previousLocation) throws ItemOverwriteException, DataAccessException {
        if(previousLocation==null){
            throw new ItemOverwriteException();
        }

        location.put(newLoc.getName(),newLoc);

    }

    @Override
    public ArrayList<Location> getAllChangedLocations() throws DataAccessException, NotFoundException {
        return (ArrayList)location.values();
    }

    @Override
    public Location getLocation(String name) throws DataAccessException, NotFoundException {
        return location.get(name);
    }


    @Override
    public void deleteLocationChange(String name) throws DataAccessException, NotFoundException {
        location.remove(name);
    }

    @Override
    public void approveLocation(String name) throws DataAccessException, NotFoundException {
        location.remove(name);
    }

    @Override
    public void addPerson(Person person) throws DataAccessException {

        people.add(person);
    }

    @Override
    public void updatePerson(Person person, Person previousPerson) throws DataAccessException, ItemOverwriteException {
        people.remove(person.getId());
        people.add(person);
    }

    @Override
    public ArrayList<Person> getAllChangedPeople() {
        return people;
    }

    @Override
    public Person getPerson(int id) throws DataAccessException, NotFoundException {
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