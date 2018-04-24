package database;

import database.interfaces.IConnector;
import data.Location;
import data.Person;
import data.Suggestion;

import java.util.HashMap;

public class DummyConnector implements IConnector {
    @Override
    public HashMap<String, Location> getLocations() {
        return null;
    }

    @Override
    public HashMap<String, Location> getLocations(String stringMatch) {
        return null;
    }

    @Override
    public HashMap<String, Suggestion> getSuggestions() {
        return null;
    }

    @Override
    public Suggestion getSuggestion(int id) {
        return null;
    }

    @Override
    public HashMap<String, Person> getPersons() {
        return null;
    }

    @Override
    public HashMap<String, Person> getPersons(String stringMatch) {
        return null;
    }

    @Override
    public void updateLocation(Location location) {

    }

    @Override
    public void deleteLocation(Person person) {

    }

    @Override
    public void updateSuggestion(Suggestion suggestion) {

    }

    @Override
    public void deleteSuggestion(int id) {

    }

    @Override
    public void updatePerson(Person person) {

    }

    @Override
    public void deletePerson(int id) {

    }

    @Override
    public void createLocation(Location location) {

    }

    @Override
    public void createPerson(Person person) {

    }
}
