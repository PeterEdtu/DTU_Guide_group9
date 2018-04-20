package api.interfaces;

import data.Location;
import data.Person;
import data.Suggestion;

import java.util.HashMap;

public interface IConnector {
    HashMap<String, Location> getLocations();

    HashMap<String, Location> getLocations(String stringMatch);

    HashMap<String, Suggestion> getSuggestions();

    Suggestion getSuggestion(int id);

    HashMap<String, Person> getPersons();

    HashMap<String, Person> getPersons(String stringMatch);

    void updateLocation(Location location);

    void deleteLocation(Person person);

    void updateSuggestion(Suggestion suggestion);

    void deleteSuggestion(int id);

    void updatePerson(Person person);

    void deletePerson(int id);

    void createLocation(Location location);

    void createPerson(Person person);

    }

