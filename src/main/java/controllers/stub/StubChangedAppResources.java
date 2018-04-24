package controllers.stub;

import api.interfaces.IChangedAppResources;
import controllers.exceptions.DataAccessException;
import controllers.exceptions.ItemOverwriteException;
import controllers.exceptions.NotFoundException;
import data.Location;
import data.Person;
import database.DummyConnector;

import java.util.ArrayList;

public class StubChangedAppResources implements IChangedAppResources {

    DummyConnector connector = new DummyConnector();

    private static StubChangedAppResources controller = null;

    private StubChangedAppResources(){}

    public static synchronized StubChangedAppResources getInstance(){
        if(controller == null){
            controller = new StubChangedAppResources();
        }

        return controller;
    }

    @Override
    public void addLocation(Location loc) throws DataAccessException {

    }

    @Override
    public void updateLocation(Location newLoc, Location previousLocation) throws ItemOverwriteException, DataAccessException {

    }

    @Override
    public ArrayList<Location> getAllChangedLocations() throws DataAccessException, NotFoundException {
        return null;
    }

    @Override
    public Location getLocation(String name) throws DataAccessException, NotFoundException {
        return null;
    }


    @Override
    public void deleteLocationChange(int id) throws DataAccessException, NotFoundException {

    }

    @Override
    public void approveLocation(int id) throws DataAccessException, NotFoundException {

    }

    @Override
    public void putPerson(Person person) throws DataAccessException {
    }

    @Override
    public void updatePerson(Person person, Person previousPerson) throws DataAccessException, ItemOverwriteException {

    }

    @Override
    public ArrayList<Person> getAllChangedPeople() {
        return null;
    }

    @Override
    public Person getPerson(int id) throws DataAccessException, NotFoundException {
        return null;
    }

    @Override
    public void deletePersonChange(int id) {

    }

    @Override
    public void approvePerson(int id) {

    }
}