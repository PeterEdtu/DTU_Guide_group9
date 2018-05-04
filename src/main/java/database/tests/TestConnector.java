package database.tests;


import controllers.exceptions.DataAccessException;
import database.connector.Connector;

public class TestConnector {
    private static Connector connector = new Connector();
    public static int countTest = 0;

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
        System.out.println(countTest);
    }


    private static boolean testGetLocations() {
        try {
            System.out.println(connector.getLocations("X"));
            countTest++;
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

}
