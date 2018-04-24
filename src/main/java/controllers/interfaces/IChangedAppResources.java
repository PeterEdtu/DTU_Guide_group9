package controllers.interfaces;

import controllers.exceptions.DataAccessException;
import controllers.exceptions.ItemOverwriteException;
import controllers.exceptions.NotFoundException;
import data.Location;
import data.Person;

import java.util.ArrayList;

public interface IChangedAppResources {

    /**
     *
     * @param loc The location to be added.
     * @return Location that was added including id.
     */
    void addLocation(Location loc) throws DataAccessException;

    void updateLocation(Location newLoc,Location previousLocation) throws DataAccessException, ItemOverwriteException;

    ArrayList<Location> getAllChangedLocations() throws DataAccessException, NotFoundException;

    Location getLocation(String name) throws DataAccessException, NotFoundException;

    void deleteLocationChange(int id) throws DataAccessException, NotFoundException;

    void approveLocation(int id) throws DataAccessException, NotFoundException;

    void putPerson(Person person) throws DataAccessException;

    void updatePerson(Person person, Person previousPerson) throws DataAccessException, ItemOverwriteException;

    ArrayList<Person> getAllChangedPeople();

    Person getPerson(int id) throws DataAccessException, NotFoundException;

    void deletePersonChange(int id);

    void approvePerson(int id);



}
