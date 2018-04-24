package database.interfaces;

import controllers.exceptions.DataAccessException;
import data.ISuggestion;
import data.Location;
import data.Person;
import data.SuggestionLocation;

import java.util.HashMap;
import java.util.List;

public interface IConnector {

    HashMap<String, Location> getLocations(String stringMatch) throws DataAccessException;

    HashMap<Integer, ISuggestion> getSuggestions()throws DataAccessException;

    List<String> getAdmins() throws DataAccessException;

    ISuggestion getSuggestion(int id) throws DataAccessException;

    HashMap<Integer, Person> getPersons() throws DataAccessException;

    HashMap<Integer, Person> getPersons(String stringMatch) throws DataAccessException;

    void updateLocation(Location location) throws DataAccessException;

    void deleteLocation(String locationName) throws DataAccessException;

    void createSuggestion(ISuggestion suggestion) throws DataAccessException;

    void updateSuggestion(ISuggestion suggestion) throws DataAccessException;

    void deleteSuggestion(int id) throws DataAccessException;

    void updatePerson(Person person) throws DataAccessException;

    void deletePerson(int id) throws DataAccessException;

    void createLocation(Location location) throws DataAccessException;

    void createPerson(Person person) throws DataAccessException;



    }

