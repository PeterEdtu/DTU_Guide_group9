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
    void addLocation(Location loc) throws DataAccessException;

    void updateLocation(Location newLoc,Location previousLocation) throws DataAccessException, ItemOverwriteException;

    ArrayList<SuggestionLocation> getAllChangedLocations() throws DataAccessException, NotFoundException;

    Location getLocation(String name) throws DataAccessException, NotFoundException;

    void deleteLocationChange(int id) throws DataAccessException, NotFoundException;

    void approveLocation(int id) throws DataAccessException, NotFoundException;

    void addPerson(Person person) throws DataAccessException;

    void updatePerson(Person person, Person previousPerson) throws DataAccessException, ItemOverwriteException;

    ArrayList<SuggestionPerson> getAllChangedPeople() throws DataAccessException, NotFoundException;

    Person getPerson(int id) throws DataAccessException, NotFoundException;

    void deletePersonChange(int id) throws DataAccessException, NotFoundException;

    void approvePerson(int id) throws DataAccessException, NotFoundException;



}
