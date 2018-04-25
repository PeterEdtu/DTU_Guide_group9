package database.connector;

import controllers.exceptions.DataAccessException;
import data.*;
import database.interfaces.IConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DummyConnector implements IConnector {
    @Override
    public HashMap<String, Location> getLocations(String stringMatch) throws DataAccessException {
        return null;
    }

    @Override
    public HashMap<Integer, Person> getPeople(String stringMatch) throws DataAccessException {
        return null;
    }

    @Override
    public ArrayList<String> getAdmins() throws DataAccessException {
        return null;
    }

    @Override
    public void createAdmin(String adminName) throws DataAccessException {

    }

    @Override
    public void deleteAdmin(String adminName) throws DataAccessException {

    }

    @Override
    public ArrayList<SuggestionLocation> getLocationSuggestions() {
        return null;
    }

    @Override
    public ArrayList<SuggestionPerson> getPeopleSuggestions() {
        return null;
    }

    @Override
    public SuggestionLocation getLocationSuggestion(int id) throws DataAccessException {
        return null;
    }

    @Override
    public SuggestionPerson getPeopleSuggestion(int id) throws DataAccessException {
        return null;
    }

    @Override
    public void updateLocation(Location location) throws DataAccessException {

    }

    @Override
    public void deleteLocation(String locationName) throws DataAccessException {

    }

    @Override
    public void createLocationSuggestion(SuggestionLocation suggestionLocation) throws DataAccessException {

    }

    @Override
    public void updateLocationSuggestion(SuggestionLocation suggestionLocation) throws DataAccessException {

    }

    @Override
    public void deleteLocationSuggestion(int id) throws DataAccessException {

    }

    @Override
    public void createLocation(Location location) throws DataAccessException {

    }

    @Override
    public void updatePerson(Person person) throws DataAccessException {

    }

    @Override
    public void deletePerson(int id) throws DataAccessException {

    }

    @Override
    public void createPeopleSuggestion(SuggestionPerson suggestionPerson) throws DataAccessException {

    }

    @Override
    public void updatePeopleSuggestion(SuggestionPerson suggestionPerson) throws DataAccessException {

    }

    @Override
    public void deletePeopleSuggestion(int id) throws DataAccessException {

    }

    @Override
    public void createPerson(Person person) throws DataAccessException {

    }
}