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

public class Connector implements IConnector {

    //JDBC driver name, and database URL:
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/distdb";

    // Database credentials:
    private static final String USER = "root";
    private static final String PASS = "Mads";

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
     * @throws DataAccessException Exception thrown in case a SQL command fails
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
                System.out.println("Failed to close connection/statement: " + e.getMessage());
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
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
        return getPeople(stringMatch);
    }

    /**
     * @return Returns a list of all admins in the system.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
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
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
        return getAdmins();
    }

    /**
     * @param adminName Input to add to the admin table in database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void createAdmin(String adminName) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlCreateAdmin = "INSERT INTO people_admins (admin_name) VALUES (?)";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlCreateAdmin);
            preparedStatement.setString(1, adminName);
            preparedStatement.executeUpdate();
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
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
    }

    /**
     * @param adminName Input to delete from admin table in database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void deleteAdmin(String adminName) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlDeleteAdmin = "DELETE FROM people_admins WHERE admin_name = ?";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlDeleteAdmin);
            preparedStatement.setString(1, adminName);
            preparedStatement.executeUpdate();
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

    /**
     * @return Returns an arraylist of all location suggestions from the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
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
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
        return getLocationSuggestions();
    }

    /**
     * @return Return an arraylist of all people suggestions from the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
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
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
        return getPeopleSuggestions();
    }

    /**
     * @param id Integer input to search for in the location suggestion table in the database
     * @return Return a suggestionLocation with a specific ID.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public SuggestionLocation getLocationSuggestion(int id) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        SuggestionLocation suggestionLocation = null;
        String sqlGetLocationSuggestion = "SELECT * FROM suggestion_locations WHERE suggestion_loc_ID = ?";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetLocationSuggestion);
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
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
        return suggestionLocation;
    }

    /**
     * @param id Integer input to search for in the suggestion_people table in the database.
     * @return Returns a SuggestionPerson object with a specific id.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public SuggestionPerson getPeopleSuggestion(int id) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        SuggestionPerson suggestionPerson = null;
        String sqlGetPeopleSuggestion = "SELECT * FROM suggestion_people WHERE suggestion_ppl_ID = ?";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetPeopleSuggestion);
            preparedStatement.setInt(1, id);
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
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
        return suggestionPerson;
    }

    /**
     * @param location Input inserted, to be changed.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void updateLocation(Location location) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlUpdateLocation = "UPDATE locations SET loc_desc = ?, loc_floor = ?, loc_landmark = ?, loc_latitude = ?, loc_longitude = ? WHERE loc_name =?";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlUpdateLocation);
            preparedStatement.setString(1, location.getDescription());
            preparedStatement.setInt(2, location.getFloor());
            preparedStatement.setString(3, location.getLandmark());
            preparedStatement.setDouble(4, location.getLatitude());
            preparedStatement.setDouble(5, location.getLongitude());
            preparedStatement.setString(6, location.getName());

            preparedStatement.executeUpdate();
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

    /**
     * @param locationName Input to search for in the database, and remove.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void deleteLocation(String locationName) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlDeleteLocation = "DELETE * FROM locations WHERE loc_name = ?";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlDeleteLocation);
            preparedStatement.setString(1, locationName);
            preparedStatement.executeUpdate();
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

    /**
     * @param suggestionLocation Input inserted to be created in the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void createLocationSuggestion(SuggestionLocation suggestionLocation) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlCreateLocationSuggestion = "INSERT INTO suggestion_locations (suggestion_loc_author, suggestion_loc_name" +
                ", suggestion_loc_desc, suggestion_loc_floor, suggestion_loc_landmark, suggestion_loc_latitude, " +
                "suggestion_loc_longitude, suggestion_loc_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        try {
            preparedStatement = establishedConnection().prepareStatement(sqlCreateLocationSuggestion);
            preparedStatement.setString(1, suggestionLocation.getAuthor());
            preparedStatement.setString(2, suggestionLocation.getName());
            preparedStatement.setString(3, suggestionLocation.getDescription());
            preparedStatement.setInt(4, suggestionLocation.getFloor());
            preparedStatement.setString(5, suggestionLocation.getLandmark());
            preparedStatement.setDouble(6, suggestionLocation.getLatitude());
            preparedStatement.setDouble(7, suggestionLocation.getLongitude());
            preparedStatement.setDate(8, (Date) suggestionLocation.getDate());

            preparedStatement.executeUpdate();
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

    /**
     * @param suggestionLocation Inserted input to be updated in the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void updateLocationSuggestion(SuggestionLocation suggestionLocation) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlUpdateLocationSuggestion = "UPDATE suggestion_locations SET suggestion_loc_author = ?, suggestion_loc_name = ?, " +
                "suggestion_loc_desc = ?, suggestion_loc_floor = ?, suggestion_loc_landmark = ?, suggestion_loc_latitude = ?, " +
                "suggestion_loc_longitude = ?, suggestion_loc_date = ? WHERE suggestion_loc_ID = ?";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlUpdateLocationSuggestion);
            preparedStatement.setString(1, suggestionLocation.getAuthor());
            preparedStatement.setString(2, suggestionLocation.getName());
            preparedStatement.setString(3, suggestionLocation.getDescription());
            preparedStatement.setInt(4, suggestionLocation.getFloor());
            preparedStatement.setString(5, suggestionLocation.getLandmark());
            preparedStatement.setDouble(6, suggestionLocation.getLatitude());
            preparedStatement.setDouble(7, suggestionLocation.getLongitude());
            preparedStatement.setDate(8, (Date) suggestionLocation.getDate());
            preparedStatement.setInt(9, suggestionLocation.getSuggestionID());

            preparedStatement.executeUpdate();
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

    /**
     * @param id Inserted suggestion id input to be deleted from the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void deleteLocationSuggestion(int id) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlDeleteLocationSuggestion = "DELETE FROM suggestion_locations WHERE suggestion_loc_ID = ?";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlDeleteLocationSuggestion);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
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

    /**
     * @param location Inserted object to be added to the locations table in the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void createLocation(Location location) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlCreateLocation = "INSERT INTO locations (loc_name, loc_desc, loc_floor, loc_landmark, loc_latitude, loc_longitude)" +
                " VALUES (?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlCreateLocation);
            preparedStatement.setString(1, location.getName());
            preparedStatement.setString(2, location.getDescription());
            preparedStatement.setInt(3, location.getFloor());
            preparedStatement.setString(4, location.getLandmark());
            preparedStatement.setDouble(5, location.getLatitude());
            preparedStatement.setDouble(6, location.getLongitude());

            preparedStatement.executeUpdate();
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

    /**
     * @param person Inserted object to be updated in the people table, in the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void updatePerson(Person person) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlUpdatePerson = "UPDATE people SET ppl_raw_name= ?, ppl_mail = ?, ppl_desc = ?," +
                "ppl_picture = ?, ppl_role = ?, ppl_room= ? WHERE ppl_ID = ?";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlUpdatePerson);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getMail());
            preparedStatement.setString(3, person.getDescription());
            preparedStatement.setString(4, person.getPicture());
            preparedStatement.setString(5, person.getRole());
            preparedStatement.setString(6, person.getRoom());
            preparedStatement.setInt(7, person.getId());

            preparedStatement.executeUpdate();
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


    /**
     * @param id Inserted id to be deleted from the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void deletePerson(int id) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlDeletePerson = "DELETE FROM people WHERE ppl_ID= ?";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlDeletePerson);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
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

    /**
     * @param suggestionPerson Object to be created in the suggesion_people table, in the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void createPeopleSuggestion(SuggestionPerson suggestionPerson) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlCreatePeopleSuggestion = "INSERT INTO suggestion_people (suggestion_ppl_author, suggestion_ppl_name" +
                ", suggestion_ppl_mail, suggestion_ppl_desc, suggestion_ppl_picture, suggestion_ppl_role, " +
                "suggestion_ppl_room, suggestion_ppl_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlCreatePeopleSuggestion);
            preparedStatement.setString(1, suggestionPerson.getAuthor());
            preparedStatement.setString(2, suggestionPerson.getName());
            preparedStatement.setString(3, suggestionPerson.getMail());
            preparedStatement.setString(4, suggestionPerson.getDescription());
            preparedStatement.setString(5, suggestionPerson.getPicture());
            preparedStatement.setString(6, suggestionPerson.getRole());
            preparedStatement.setString(7, suggestionPerson.getRoom());
            preparedStatement.setDate(8, (Date) suggestionPerson.getDate());

            preparedStatement.executeUpdate();
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
    public void updatePeopleSuggestion(SuggestionPerson suggestionPerson) throws DataAccessException {

    }

    /**
     * @param id Inserted id to be removed from the suggestion_people table in the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void deletePeopleSuggestion(int id) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlDeletePeopleSuggestion = "DELETE FROM suggestion_people WHERE suggestion_ppl_ID= ?";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlDeletePeopleSuggestion);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
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
    public void createPerson(Person person) throws DataAccessException {

    }
}