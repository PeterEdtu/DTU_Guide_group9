package controllers;

import controllers.interfaces.IChangedAppResources;
import controllers.exceptions.DataAccessException;
import controllers.exceptions.ItemOverwriteException;
import controllers.exceptions.NotFoundException;
import data.Person;
import data.Location;
import database.DummyConnector;

import java.util.ArrayList;

public class ChangedAppResources implements IChangedAppResources {

    DummyConnector connector = new DummyConnector();

    private static ChangedAppResources controller = null;

    private ChangedAppResources(){};

    public synchronized ChangedAppResources getInstance(){
        if(controller == null){
            controller = new ChangedAppResources();
        }

        return controller;
    }

    @Override
    public void addLocation(Location loc) throws DataAccessException {
        connector.createLocation(loc);
    }

    @Override
    public void updateLocation(Location newLoc, Location previousLocation) throws ItemOverwriteException, DataAccessException {
        //Compare the previousLocation with the
        //actual (instance) location before update it
        //with the variable "newLoc" wich is the new location

        Location actualLocation = connector.getLocations(previousLocation.getName()).get(previousLocation.getName());

        Location overwrite = new Location();

        if (!actualLocation.equals(previousLocation)) {

            if (!actualLocation.getDescription().equals(previousLocation.getDescription())) {
                overwrite.setDescription(actualLocation.getDescription());
            }
            if (!(actualLocation.getFloor() == previousLocation.getFloor())) {
                overwrite.setFloor(actualLocation.getFloor());
            }
            if (!actualLocation.getLandmark().equals(previousLocation.getLandmark())) {
                overwrite.setLandmark(actualLocation.getLandmark());
            }
            if (!(actualLocation.getLatitude() == previousLocation.getLatitude())) {
                overwrite.setLatitude(actualLocation.getLatitude());
            }
            if (!(actualLocation.getLongitude() == previousLocation.getLongitude())) {
                overwrite.setLongitude(actualLocation.getLongitude());
            }
            if(!actualLocation.getName().equals(previousLocation.getName())){
                overwrite.setName(actualLocation.getName());
            }

            ItemOverwriteException exception = new ItemOverwriteException(overwrite);

            throw exception;

        }else{
            connector.updateLocation(newLoc);
        }

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
        connector.createLocation(connector.getSuggestion(id));

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