package controllers.interfaces;

import controllers.exceptions.DataAccessException;
import controllers.exceptions.ItemOverwriteException;
import controllers.exceptions.NotFoundException;
import data.*;

import java.util.ArrayList;

public interface IChangedAppResources {

    /**
     *
     * @param loc The location to be added.
     * @return Location that was added including id.
     */
    void addLocation(SuggestionLocation location) throws DataAccessException;

    void updateLocation(SuggestionLocation newLoc,SuggestionLocation previousLocation) throws DataAccessException, ItemOverwriteException;

    ArrayList<SuggestionLocation> getAllChangedLocations() throws DataAccessException, NotFoundException;

    SuggestionLocation getLocation(int id) throws DataAccessException, NotFoundException;

    void deleteLocationChange(int id) throws DataAccessException, NotFoundException;

    void approveLocation(int id) throws DataAccessException, NotFoundException;

    void addPerson(SuggestionPerson person) throws DataAccessException;

    void updatePerson(SuggestionPerson person, SuggestionPerson previousPerson) throws DataAccessException, ItemOverwriteException;

    ArrayList<SuggestionPerson> getAllChangedPeople() throws DataAccessException, NotFoundException;

    SuggestionLocation getPerson(int id) throws DataAccessException, NotFoundException;

    void deletePersonChange(int id) throws DataAccessException, NotFoundException;

    void approvePerson(int id) throws DataAccessException, NotFoundException;



}
