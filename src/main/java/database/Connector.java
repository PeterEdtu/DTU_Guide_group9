package database;//STEP 1. Import required packages

import data.ISuggestion;
import database.interfaces.IConnector;
import controllers.exceptions.DataAccessException;
import data.Location;
import data.Person;
import data.SuggestionLocation;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class Connector implements IConnector {

    //JDBC driver name, and database URL:
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/distdb";

    // Database credentials:
    static final String USER = "root";
    static final String PASS = "Mads";

    // Find a way to add information to Hashmap
    @Override
    public HashMap<String, Location> getLocations(String stringMatch) {
        establishedConnection();
        Statement stmt = null;
        Location location = null;

        //Execute a query.
        try {
            stmt = establishedConnection().createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement. Verify connection to database: " + e.getMessage());
        }

        String sql;
        sql = "SELECT * FROM locations WHERE loc_name LIKE '%" + stringMatch + "%';";

        try {
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                location.setName(resultSet.getString("loc_name"));
                location.setDescription(resultSet.getString("loc_desc"));
                location.setFloor(resultSet.getInt("loc_floor"));
                location.setLandmark(resultSet.getString("loc_landmark"));
                location.setLatitude(resultSet.getDouble("loc_latitude"));
                location.setLongitude(resultSet.getDouble("loc_longitude"));
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
        return getLocations(stringMatch);
    }

    //Possible redundant, needs further work/removal.
    @Override
    public HashMap<Integer, ISuggestion> getSuggestions() throws DataAccessException {
        establishedConnection();
        Statement stmt = null;
        SuggestionLocation suggestionLocation = null;
        //Execute a query.
        try {
            stmt = establishedConnection().createStatement();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to create statement. Verify connection to database: " + e.getMessage());
        }

        String sql;
        sql = "SELECT * FROM suggestions;";

        try {
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                suggestionLocation.setAuthor(resultSet.getString("suggestion_person_name"));
                suggestionLocation.setName(resultSet.getString("suggestion_loc_name"));
                suggestionLocation.setDescription(resultSet.getString("suggestion_loc_desc"));
                suggestionLocation.setFloor(resultSet.getInt("suggestion_loc_floor"));
                suggestionLocation.setLandmark(resultSet.getString("suggestion_loc_landmark"));
                suggestionLocation.setLatitude(resultSet.getDouble("suggestion_loc_latitude"));
                suggestionLocation.setLongitude(resultSet.getDouble("suggestion_loc_longitude"));
                suggestionLocation.setDate(resultSet.getDate("suggestion_date"));
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute: " + e.getMessage());
        }

        try {
            establishedConnection().close();
            stmt.close();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to close connection/statement: " + e.getMessage());
        }
        return getSuggestions();

    }

    @Override
    public List<String> getAdmins() throws DataAccessException {
        establishedConnection();
        Statement stmt = null;

        //Execute query.
        try {
            stmt = establishedConnection().createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement. Verify connection to database: " + e.getMessage());
        }

        String sql;
        sql = "SELECT * FROM people_admins;";

        try {
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                getAdmins().add(resultSet.getString("admin_name"));
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute: " + e.getMessage());
        }

        return getAdmins();
    }

    @Override
    public SuggestionLocation getSuggestion(int id) {
        establishedConnection();
        Statement stmt = null;
        SuggestionLocation suggestionLocation = null;

        //Execute a query.
        try {
            stmt = establishedConnection().createStatement();
        } catch (SQLException e) {
            System.out.println("Failed to create statement. Verify connection to database: " + e.getMessage());
        }

        String sql;
        sql = "SELECT * FROM suggestion_locations WHERE loc_name LIKE '%" + id + "%';";

        try {
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                suggestionLocation.setAuthor(resultSet.getString("suggestion_person_name"));
                suggestionLocation.setName(resultSet.getString("suggestion_loc_name"));
                suggestionLocation.setDescription(resultSet.getString("suggestion_loc_desc"));
                suggestionLocation.setFloor(resultSet.getInt("suggestion_loc_floor"));
                suggestionLocation.setLandmark(resultSet.getString("suggestion_loc_landmark"));
                suggestionLocation.setLatitude(resultSet.getDouble("suggestion_loc_latitude"));
                suggestionLocation.setLongitude(resultSet.getDouble("suggestion_loc_longitude"));
                suggestionLocation.setDate(resultSet.getDate("suggestion_date"));
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
        return getSuggestion(id);
    }

    //Redundant/removable?
    @Override
    public HashMap<Integer, Person> getPersons() throws DataAccessException {
        return null;
    }

    @Override
    public HashMap<Integer, Person> getPersons(String stringMatch) throws DataAccessException {
        establishedConnection();
        Statement stmt = null;
        Person person = null;
        //Execute a query.
        try {
            stmt = establishedConnection().createStatement();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to create statement. Verify connection to database: " + e.getMessage());
        }

        String sql;
        sql = "SELECT * FROM people WHERE ppl_raw_name = '" + stringMatch + "';";

        try {
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                person.setId(resultSet.getInt("ppl_ID"));
                person.setName(resultSet.getString("ppl_raw_name"));
                person.setMail(resultSet.getString("ppl_mail"));
                person.setDesc(resultSet.getString("ppl_desc"));
                person.setPicture(resultSet.getString("ppl_picture"));
                person.setRole(resultSet.getString("ppl_role"));
                person.setRoom(resultSet.getString("ppl_room"));
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute: " + e.getMessage());
        }

        try {
            establishedConnection().close();
            stmt.close();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to close connection/statement: " + e.getMessage());
        }
        return getPersons(stringMatch);

    }


    @Override
    public void createLocation(Location location) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;

        //Execute a query.
        try {
            String sqlCreateLocation = "INSERT INTO locations (loc_name, loc_desc, loc_floor, loc_landmark, loc_latitude, loc_longitude)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = establishedConnection().prepareStatement(sqlCreateLocation);
            preparedStatement.setString(1, location.getName());
            preparedStatement.setString(2, location.getDescription());
            preparedStatement.setInt(3, location.getFloor());
            preparedStatement.setString(4, location.getLandmark());
            preparedStatement.setDouble(5, location.getLatitude());
            preparedStatement.setDouble(6, location.getLongitude());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }

        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());

            }
        }
    }


    @Override
    public void updateLocation(Location location) {

    }

    @Override
    public void createSuggestion(ISuggestion suggestion) throws DataAccessException {

    }

    @Override
    public void updateSuggestion(ISuggestion suggestion) throws DataAccessException {

    }


    @Override
    public void deleteLocation(String locationName) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;

        //Execute a query.
        try {
            String sql = "DELETE FROM locations WHERE loc_name = ?";
            preparedStatement = establishedConnection().prepareStatement(sql);
            preparedStatement.setString(1, locationName);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to create statement. Verify connection to database: " + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
            } catch (SQLException e) {
                throw new DataAccessException("Failed to close connection/statement: " + e.getMessage());
            }
        }
    }


    @Override
    public void deleteSuggestion(int id) {
        establishedConnection();
        PreparedStatement preparedStatement = null;

        //Execute a query.
        try {
            String sqlDeleteSuggestion = "DELETE FROM suggestions WHERE suggestion_loc_id= ?";
            preparedStatement = establishedConnection().prepareStatement(sqlDeleteSuggestion);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to create statement. Verify connection to database: " + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());

            }
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
        PreparedStatement preparedStatement = null;

        //Execute a query.
        try {
            String sql = "DELETE FROM people WHERE ppl_ID = ?";
            preparedStatement = establishedConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to create statement. Verify connection to database: " + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());

            }
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
