package database;

import api.interfaces.IConnector;
import controllers.exceptions.DataAccessException;
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
    public HashMap<Integer, SuggestionLocation> getSuggestions() throws DataAccessException {
        return null;
    }

    @Override
    public List<String> getAdmins() throws DataAccessException {
        return null;
    }

    @Override
    public SuggestionLocation getSuggestion(int id) throws DataAccessException {
        return null;
    }

    @Override
    public HashMap<Integer, Person> getPersons() throws DataAccessException {
        return null;
    }

    @Override
    public HashMap<Integer, Person> getPersons(String stringMatch) throws DataAccessException {
        return null;
    }

    @Override
    public void updateLocation(Location location) throws DataAccessException {

    }

    @Override
    public void deleteLocation(Person person) throws DataAccessException {

    }

    @Override
    public void deleteLocation(Location location) throws DataAccessException {

    }

    @Override
    public void createSuggestion(SuggestionLocation suggestionLocation) throws DataAccessException {

    }

    @Override
    public void updateSuggestion(SuggestionLocation suggestionLocation) throws DataAccessException {

    }

    @Override
    public void deleteSuggestion(int id) throws DataAccessException {

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
