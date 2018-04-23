package api.interfaces;

import controllers.exceptions.DataAccessException;
import data.Location;
import data.Person;
import data.Suggestion;

import java.util.HashMap;

public interface IConnector {

    HashMap<String, Location> getLocations(String stringMatch) throws DataAccessException;

    HashMap<String, Suggestion> getSuggestions()throws DataAccessException;

    String[] getAdmins() throws DataAccessException;

    Suggestion getSuggestion(int id) throws DataAccessException;

    HashMap<Integer, Person> getPersons() throws DataAccessException;

    HashMap<Integer, Person> getPersons(String stringMatch) throws DataAccessException;

    void updateLocation(Location location) throws DataAccessException;

    void deleteLocation(Person person) throws DataAccessException;

    void deleteLocation(Location location) throws DataAccessException;

    void createSuggestion(Suggestion suggestion) throws DataAccessException;

    void updateSuggestion(Suggestion suggestion) throws DataAccessException;

    void deleteSuggestion(int id) throws DataAccessException;

    void updatePerson(Person person) throws DataAccessException;

    void deletePerson(int id) throws DataAccessException;

    void createLocation(Location location) throws DataAccessException;

    void createPerson(Person person) throws DataAccessException;



    }

