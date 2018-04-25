package database.connector;

import controllers.exceptions.DataAccessException;
import data.Location;
import data.Person;
import data.SuggestionLocation;
import data.SuggestionPerson;
import database.interfaces.IConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Connector implements IConnector {

    //JDBC driver name, and database URL:
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/distdb";

    // Database credentials:
    static final String USER = "root";
    static final String PASS = "Mads";

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

    /**
     * @param stringMatch Input to search for, in the 'locations' table.
     * @return Returns getLocations hashmap.
     * @throws DataAccessException
     */
    @Override
    public HashMap<String, Location> getLocations(String stringMatch) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        Location location = null;
        String sqlGetLocations = "SELECT * FROM locations WHERE loc_name LIKE ?";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetLocations);
            preparedStatement.setString(1, stringMatch);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                location.setName(resultSet.getString("loc_name"));
                location.setDescription(resultSet.getString("loc_desc"));
                location.setFloor(resultSet.getInt("loc_floor"));
                location.setLandmark(resultSet.getString("loc_landmark"));
                location.setLatitude(resultSet.getDouble("loc_latitude"));
                location.setLongitude(resultSet.getDouble("loc_longitude"));

                getLocations(stringMatch).put(location.getName(), location);
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
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
        return getLocations(stringMatch);
    }

    /**
     * @param stringMatch Input to search for, in the 'people' table.
     * @return Returns getPeople hashmap.
     * @throws DataAccessException
     */
    @Override
    public HashMap<Integer, Person> getPeople(String stringMatch) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        Person person = null;
        String sqlGetPeople = "SELECT * FROM people WHERE ppl_name LIKE ?";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetPeople);
            preparedStatement.setString(1, stringMatch);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                person.setId(resultSet.getInt("ppl_ID"));
                person.setName(resultSet.getString("ppl_name"));
                person.setMail(resultSet.getString("ppl_mail"));
                person.setDescription(resultSet.getString("ppl_desc"));
                person.setPicture(resultSet.getString("ppl_picture"));
                person.setRole(resultSet.getString("ppl_role"));
                person.setRoom(resultSet.getString("ppl_room"));

                getPeople(stringMatch).put(person.getId(), person);
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
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
        return getPeople(stringMatch);
    }

    /**
     * @return Returns a list of all admins in the system.
     * @throws DataAccessException
     */
    @Override
    public ArrayList<String> getAdmins() throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlGetAdmins = "SELECT * FROM people_admins";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetAdmins);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                getAdmins().add("admin_name");
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
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
        return getAdmins();
    }

    @Override
    public void createAdmin(String adminName) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlCreateAdmin = "INSERT INTO people_admins (admin_name) VALUES (?)";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlCreateAdmin);
            preparedStatement.setString(1, adminName);
            preparedStatement.executeQuery();
            System.out.println("Added a new admin to admin-table. New admin name: " + adminName);
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
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
    public void deleteAdmin(String adminName) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlDeleteAdmin = "DELETE FROM people_admins WHERE admin_name = ?";
        
        try{
            preparedStatement = establishedConnection().prepareStatement(sqlDeleteAdmin);
            preparedStatement.setString(1, adminName);
            preparedStatement.executeQuery();
        }

        catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
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

    /**
     * @return Returns an arraylist of all location suggestions from the database.
     * @throws DataAccessException
     */
    @Override
    public ArrayList<SuggestionLocation> getLocationSuggestions() throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        SuggestionLocation suggestionLocation = null;
        String sqlGetLocationSuggestions = "SELECT * FROM suggestion_locations";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetLocationSuggestions);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                suggestionLocation.setSuggestionID(resultSet.getInt("suggestion_loc_ID"));
                suggestionLocation.setAuthor(resultSet.getString("suggestion_loc_author"));
                suggestionLocation.setName(resultSet.getString("suggestion_loc_name"));
                suggestionLocation.setDescription(resultSet.getString("suggestion_loc_desc"));
                suggestionLocation.setFloor(resultSet.getInt("suggestion_loc_floor"));
                suggestionLocation.setLandmark(resultSet.getString("suggestion_loc_landmark"));
                suggestionLocation.setLatitude(resultSet.getDouble("suggestion_loc_latitude"));
                suggestionLocation.setLongitude(resultSet.getDouble("suggestion_loc_longitude"));
                suggestionLocation.setDate(resultSet.getDate("suggestion_loc_date"));

                getLocationSuggestions().add(suggestionLocation);
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
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
        return getLocationSuggestions();
    }

    /**
     * @return Return an arraylist of all people suggestions from the database.
     * @throws DataAccessException
     */
    @Override
    public ArrayList<SuggestionPerson> getPeopleSuggestions() throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        SuggestionPerson suggestionPerson = null;
        String sqlGetPeopleSuggestions = "SELECT * FROM suggestion_people";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetPeopleSuggestions);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                suggestionPerson.setSuggestionID(resultSet.getInt("suggestion_ppl_ID"));
                suggestionPerson.setAuthor(resultSet.getString("suggestion_ppl_author"));
                suggestionPerson.setName(resultSet.getString("suggestion_ppl_name"));
                suggestionPerson.setMail(resultSet.getString("suggestion_ppl_mail"));
                suggestionPerson.setDescription(resultSet.getString("suggestion_ppl_desc"));
                suggestionPerson.setPicture(resultSet.getString("suggestion_ppl_picture"));
                suggestionPerson.setRole(resultSet.getString("suggestion_ppl_role"));
                suggestionPerson.setRoom(resultSet.getString("suggestion_ppl_room"));
                suggestionPerson.setDate(resultSet.getDate("suggestion_ppl_date"));

                getPeopleSuggestions().add(suggestionPerson);
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
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
        return getPeopleSuggestions();
    }

    @Override
    public SuggestionLocation getLocationSuggestion(int id) throws DataAccessException {
        return null;
    }

    @Override
    public SuggestionPerson getPeopleSuggestion(int id) throws DataAccessException {
        return null;
    }

    @Override
    public void updateLocation(Location location) throws DataAccessException {

    }

    @Override
    public void deleteLocation(String locationName) throws DataAccessException {

    }

    @Override
    public void createLocationSuggestion(SuggestionLocation suggestionLocation) throws DataAccessException {

    }

    @Override
    public void updateLocationSuggestion(SuggestionLocation suggestionLocation) throws DataAccessException {

    }

    @Override
    public void deleteLocationSuggestion(int id) throws DataAccessException {

    }

    @Override
    public void createLocation(Location location) throws DataAccessException {

    }

    @Override
    public void updatePerson(Person person) throws DataAccessException {

    }

    @Override
    public void deletePerson(int id) throws DataAccessException {

    }

    @Override
    public void createPeopleSuggestion(SuggestionPerson suggestionPerson) throws DataAccessException {

    }

    @Override
    public void updatePeopleSuggestion(SuggestionPerson suggestionPerson) throws DataAccessException {

    }

    @Override
    public void deletePeopleSuggestion(int id) throws DataAccessException {

    }

    @Override
    public void createPerson(Person person) throws DataAccessException {

    }
}
