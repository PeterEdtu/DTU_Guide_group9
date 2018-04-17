package api.interfaces;

import data.Person;

import javax.xml.stream.Location;
import java.util.ArrayList;

public interface IChangedAppResources {

    /**
     *
     * @param loc The location to be added.
     * @return Location that was added including id.
     */
    Location addLocation(Location loc);

    void updateLocation(Location loc,Location previousLocation);

    ArrayList<Location> getAllChangedLocations();

    Location getLocation(int id);

    void deleteLocationChange(int id);

    void approveLocation(int id);


    Person putPerson(Person person);

    void updatePerson(Person person, Person previousPerson);

    ArrayList<Person> getAllChangedPeople();

    Person getPerson(int id);

    void deletePersonChange(int id);

    void approvePerson(int id);



}
