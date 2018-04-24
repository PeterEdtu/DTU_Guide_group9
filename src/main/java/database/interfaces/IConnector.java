package database.interfaces;

import controllers.exceptions.DataAccessException;
import data.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IConnector {

    HashMap<String, Location> getLocations(String stringMatch) throws DataAccessException;

    HashMap<Integer, Person> getPeople(String stringMatch) throws DataAccessException;


    List<String> getAdmins() throws DataAccessException;


    ArrayList<SuggestionLocation> getLocationSuggestions();

    ArrayList<SuggestionPerson> getPeopleSuggestions();



    SuggestionLocation getLocationSuggestion(int id) throws DataAccessException;

    SuggestionPerson getPeopleSuggestion(int id) throws DataAccessException;


    void updateLocation(Location location) throws DataAccessException;

    void deleteLocation(String locationName) throws DataAccessException;

    void createLocationSuggestion(SuggestionLocation suggestionLocation) throws DataAccessException;

    void updateLocationSuggestion(SuggestionLocation suggestionLocation) throws DataAccessException;

    void deleteLocationSuggestion(int id) throws DataAccessException;

    void createLocation(Location location) throws DataAccessException;



    void updatePerson(Person person) throws DataAccessException;

    void deletePerson(int id) throws DataAccessException;

    void createPeopleSuggestion(SuggestionPerson suggestionPerson) throws DataAccessException;

    void updatePeopleSuggestion(SuggestionPerson suggestionPerson) throws DataAccessException;

    void deletePeopleSuggestion(int id) throws DataAccessException;

    void createPerson(Person person) throws DataAccessException;



    }

