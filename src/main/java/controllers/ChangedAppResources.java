package controllers;

import api.interfaces.IChangedAppResources;
import data.Person;
import database.Connector;
import data.Location;
import data.Person;
import data.Suggestion;
import database.DummyConnector;

import java.util.ArrayList;

public class ChangedAppResources implements IChangedAppResources{

    DummyConnector connector = new DummyConnector();

    @Override
    public Location addLocation(Location loc) {
        return null;
    }

    @Override
    public void updateLocation(Location newLoc, Location previousLocation) { //Compare the previousLocation with the
                                                                        //actual (instance) location before update it
                                                                    //with the variable "newLoc" wich is the new location

    Location actualLocation = connector.getLocations().get(previousLocation.name);



    }

    @Override
    public ArrayList<Location> getAllChangedLocations() {
        return null;
    }

    @Override
    public Location getLocation(int id) {
        return null;
    }

    @Override
    public void deleteLocationChange(int id) {

    }

    @Override
    public void approveLocation(int id) {

    }

    @Override
    public Person putPerson(Person person) {
        return null;
    }

    @Override
    public void updatePerson(Person person, Person previousPerson) {

    }

    @Override
    public ArrayList<Person> getAllChangedPeople() {
        return null;
    }

    @Override
    public Person getPerson(int id) {
        return null;
    }

    @Override
    public void deletePersonChange(int id) {

    }

    @Override
    public void approvePerson(int id) {

    }
}
