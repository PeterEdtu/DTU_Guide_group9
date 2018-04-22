package database;//STEP 1. Import required packages

import api.interfaces.IConnector;
import data.Location;
import data.Person;
import data.Suggestion;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;

public class Connector implements IConnector {

    //JDBC driver name, and database URL:
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/distdb";

    // Database credentials:
    static final String USER = "root";
    static final String PASS = "Mads";

    // Find a way to add information to Hashmap
    @Override
    public HashMap<String, Location> getLocations() {
        establishedConnection();
        Statement stmt = null;

        //Execute a query.
        try {
            stmt = establishedConnection().createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement. Verify connection to database: " + e.getMessage());
        }

        String sql;
        sql = "SELECT * FROM locations;";

        try {
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                String loc_name = resultSet.getString("loc_name");
                String loc_desc = resultSet.getString("loc_desc");
                int loc_floor = resultSet.getInt("loc_floor");
                String loc_landmark = resultSet.getString("loc_landmark");
                Double loc_latitude = resultSet.getDouble("loc_latitude");
                Double loc_longitude = resultSet.getDouble("loc_longitude");

                System.out.println("Location name: " + loc_name + ", Location description: " + loc_desc + ", Location floor: " + loc_floor + ", Location landmark: " + loc_landmark + ", Location latitude: " + loc_latitude + ", Location longitude: " + loc_longitude + ".");
            }
        } catch (SQLException e) {
            System.out.println("SQL command failed to execute: " + e.getMessage());
        }

        try {
            establishedConnection().close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Failed to close connection/statement: " + e.getMessage());
        }
        return null;
    }

    @Override
    public HashMap<String, Location> getLocations(String stringMatch) {
        return null;
    }

    //Experimental - Needs further work!
    @Override
    public HashMap<String, Suggestion> getSuggestions() {
        establishedConnection();
        Statement stmt = null;
        Suggestion suggestion = null;
        //Execute a query.
        try {
            stmt = establishedConnection().createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement. Verify connection to database: " + e.getMessage());
        }

        String sql;
        sql = "SELECT * FROM suggestions;";

        try {
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                suggestion.setAuthor(resultSet.getString("suggestion_person_name"));
                suggestion.setDate(resultSet.getDate("suggestion_date"));
                getSuggestions().put("", suggestion);

                int suggestion_loc_ID = resultSet.getInt("suggestion_loc_ID");
                String suggestion_person_name = resultSet.getString("suggestion_person_name");
                String suggestion_loc_name = resultSet.getString("suggestion_loc_name");
                String suggestion_loc_desc = resultSet.getString("suggestion_loc_desc");
                int suggestion_loc_floor = resultSet.getInt("suggestion_loc_floor");
                String suggestion_loc_landmark = resultSet.getString("suggestion_loc_landmark");
                Double suggestion_loc_latitude = resultSet.getDouble("suggestion_loc_latitude");
                Double suggestion_loc_longitude = resultSet.getDouble("suggestion_loc_longitude");
                Date suggestion_date = resultSet.getDate("suggestion_date");

                System.out.println("Suggestion ID: " + suggestion_loc_ID + ", Person suggesting: " + suggestion_person_name + ", Suggestion name: " + suggestion_loc_name + ", Suggestion description: " + suggestion_loc_desc + ", Suggestion floor: " + suggestion_loc_floor + ", Suggestion landmark: " + suggestion_loc_landmark + ", Suggestion latitude: " + suggestion_loc_latitude + ", Suggestion longitude: " + suggestion_loc_longitude + ", Suggestion date: " + suggestion_date + ".");
            }
        } catch (SQLException e) {
            System.out.println("SQL command failed to execute: " + e.getMessage());
        }

        try {
            establishedConnection().close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Failed to close connection/statement: " + e.getMessage());
        }
        return null;

    }

    @Override
    public Suggestion getSuggestion(int id) {
        return null;
    }

    @Override
    public HashMap<String, Person> getPersons() {
        return null;
    }

    @Override
    public HashMap<String, Person> getPersons(String stringMatch) {
        return null;
    }

    @Override
    public void createLocation(Location location) {

    }


    @Override
    public void updateLocation(Location location) {

    }

    @Override
    public void deleteLocation(Person person) {

    }

    @Override
    public void deleteLocation(Location location) {
        establishedConnection();
        Statement stmt = null;

        //Execute a query.
        try {
            stmt = establishedConnection().createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement. Verify connection to database: " + e.getMessage());
        }

        String sql;
        sql = "DELETE FROM locations WHERE loc_name = '" + location.name + "';";

        try {
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("SQL command failed to execute: " + e.getMessage());
        }

        try {
            establishedConnection().close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Failed to close connection/statement: " + e.getMessage());
        }

    }

    @Override
    public void createSuggestion(Suggestion suggestion) {

    }

    @Override
    public void updateSuggestion(Suggestion suggestion) {

    }

    @Override
    public void deleteSuggestion(int id) {
        establishedConnection();
        Statement stmt = null;

        //Execute a query.
        try {
            stmt = establishedConnection().createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement. Verify connection to database: " + e.getMessage());
        }

        String sql;
        sql = "DELETE FROM suggestion WHERE suggestion_loc_ID = '" + id + "';";

        try {
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("SQL command failed to execute: " + e.getMessage());
        }

        try {
            establishedConnection().close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Failed to close connection/statement: " + e.getMessage());
        }

    }

    @Override
    public void createPerson(Person person) {

    }

    @Override
    public void updatePerson(Person person) {

    }

    @Override
    public void deletePerson(int id) {
        establishedConnection();
        Statement stmt = null;

        //Execute a query.
        try {
            stmt = establishedConnection().createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement. Verify connection to database: " + e.getMessage());
        }

        String sql;
        sql = "DELETE FROM people WHERE ppl_ID = '" + id + "';";

        try {
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("SQL command failed to execute: " + e.getMessage());
        }

        try {
            establishedConnection().close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Failed to close connection/statement: " + e.getMessage());
        }
    }


    private Connection establishedConnection() {
        Connection conn = null;
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected successfully!");
        } catch (SQLException e) {
            System.out.println("Connection to database failed: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
        return conn;
    }
}