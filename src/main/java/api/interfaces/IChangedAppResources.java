package api.interfaces;

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
    Location addLocation(Location loc) throws DataAccessException;

    void updateLocation(Location newLoc,Location previousLocation) throws DataAccessException, ItemOverwriteException;

    ArrayList<Location> getAllChangedLocations() throws NotFoundException;

    Location getLocation(int id) throws NotFoundException;

    void deleteLocationChange(int id) throws DataAccessException, NotFoundException;

    void approveLocation(int id);


    Person putPerson(Person person);

    void updatePerson(Person person, Person previousPerson);

    ArrayList<Person> getAllChangedPeople();

    Person getPerson(int id);

    void deletePersonChange(int id);

    void approvePerson(int id);



}
