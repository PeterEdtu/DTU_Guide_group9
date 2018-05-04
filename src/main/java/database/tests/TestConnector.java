package database.tests;


import controllers.exceptions.DataAccessException;
import data.Location;
import data.Person;
import data.SuggestionLocation;
import database.connector.Connector;

import java.util.Date;

public class TestConnector {
    private static Connector connector = new Connector();


    public static void main(String[] args) {
        testGetLocations();
        testGetPeople();
        testGetAdmins();
        testGetTags();
        testCreateAdmin();
        testDeleteAdmin();
        testGetLocationSuggestions();
        testGetPeopleSuggestions();
        testGetPeopleSuggestionID();
        testUpdateLocation();
        //testDeleteLocation();
        testCreatePerson();
        testCreateLocationSuggestion();
    }


    private static boolean testGetLocations() {
        try {
            System.out.println(connector.getLocations("X"));
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean testGetPeople() {
        try {
            System.out.println(connector.getPeople("E"));
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean testGetAdmins() {
        try {
            System.out.println(connector.getAdmins());
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean testGetTags() {
        try {
            System.out.println(connector.getTags());
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean testCreateAdmin() {
        try {
            connector.createAdmin("test1");
            System.out.println(connector.getAdmins());
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean testDeleteAdmin() {
        try {
            connector.deleteAdmin("test1");
            System.out.println(connector.getAdmins());
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean testGetLocationSuggestions() {
        try {
            System.out.println(connector.getLocationSuggestions());
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }


    private static boolean testGetPeopleSuggestions() {
        try {
            System.out.println(connector.getPeopleSuggestions());
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean testGetPeopleSuggestionID() {
        try {
            System.out.println(connector.getLocationSuggestion(1));
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean testUpdateLocation() {
        try {
            Location tempLoc = new Location();
            tempLoc.setName("X1.54");
            tempLoc.setDescription("testdesc");
            tempLoc.setFloor(1);
            tempLoc.setLandmark("testlandmark");
            tempLoc.setLatitude(12.24563);
            tempLoc.setLongitude(12.2125);

            connector.updateLocation(tempLoc);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

  /*  private static boolean testDeleteLocation() {
        try {
            connector.deleteLocation("V2.01");
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }*/

    private static boolean testCreatePerson() {
        try {
            Person person = new Person();

            person.setName("test one");
            person.setMail("mail@test.com");
            person.setDescription("test desc");
            person.setPicture("test_pic.jpg");
            person.setRole("Test role");
            person.setRoom("Testroom");

            connector.createPerson(person);
            System.out.println("Created person: " + person.getName());
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean testCreateLocationSuggestion() {
        try {

            Date date = new Date();


            SuggestionLocation suggestionLocation = new SuggestionLocation();
            suggestionLocation.setAuthor("TestAuthor");
            suggestionLocation.setName("Locationname");
            suggestionLocation.setDescription("Testdesc");
            suggestionLocation.setFloor(1);
            suggestionLocation.setLandmark("Testlandmark");
            suggestionLocation.setLatitude(12.24563);
            suggestionLocation.setLongitude(12.2125);
            suggestionLocation.setDate(date);

            connector.updateLocation(suggestionLocation);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }


}
