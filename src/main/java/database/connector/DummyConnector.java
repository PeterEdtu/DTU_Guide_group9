package database.connector;

import controllers.exceptions.DataAccessException;
import data.ISuggestion;
import database.interfaces.IConnector;
import data.Location;
import data.Person;
import data.SuggestionLocation;

import java.util.HashMap;
import java.util.List;

public class DummyConnector implements IConnector {
    @Override
    public HashMap<String, Location> getLocations(String stringMatch) throws DataAccessException {
        return null;
    }

    @Override
    public HashMap<Integer, ISuggestion> getSuggestions() throws DataAccessException {
        return null;
    }

    @Override
    public List<String> getAdmins() throws DataAccessException {
        return null;
    }

    @Override
    public SuggestionLocation getLocationSuggestion(int id) throws DataAccessException {
        return null;
    }

    @Override
    public HashMap<Integer, Person> getPeople() throws DataAccessException {
        return null;
    }

    @Override
    public HashMap<Integer, Person> getPeople(String stringMatch) throws DataAccessException {
        return null;
    }

    @Override
    public void updateLocation(Location location) throws DataAccessException {

    }

    @Override
    public void deleteLocation(String locationName) throws DataAccessException {

    }

    @Override
    public void createLocationSuggestion(ISuggestion suggestion) throws DataAccessException {

    }

    @Override
    public void updateSuggestion(ISuggestion suggestion) throws DataAccessException {

    }


    @Override
    public void updateSuggestion(SuggestionLocation suggestionLocation) throws DataAccessException {

    }

    @Override
    public void deleteLocationSuggestion(int id) throws DataAccessException {

    }

    @Override
    public void updatePerson(Person person) throws DataAccessException {

    }

    @Override
    public void deletePerson(int id) throws DataAccessException {

    }

    @Override
    public void createLocation(Location location) throws DataAccessException {

    }

    @Override
    public void createPerson(Person person) throws DataAccessException {

    }
}
