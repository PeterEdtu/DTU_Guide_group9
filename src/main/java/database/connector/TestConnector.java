package database.connector;


import controllers.exceptions.DataAccessException;

public class TestConnector {
    private static Connector connector = new Connector();

    public static void main(String[] args) {
        testGetLocations();
    }

    private static boolean testGetLocations() {
        try {
            System.out.println(connector.getLocations("m"));
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }

    }


}
