package database.connector;//STEP 1. Import required packages

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
        String sqlGetLocations = "SELECT * FROM locations WHERE loc_name LIKE ?";
        try {
            PreparedStatement preparedStatement = establishedConnection().prepareStatement(sqlGetLocations);
            preparedStatement.setString(1, stringMatch);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
//                getLocations(stringMatch).put(resultSet.getString("ppl_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return getLocations(stringMatch);
    }

    //Needs further work.
    @Override
    public HashMap<Integer, ISuggestion> getSuggestions() throws DataAccessException {
        establishedConnection();
        String sql = "SELECT * FROM suggestions;";
        try {
            PreparedStatement preparedStatement = establishedConnection().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
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
    public SuggestionLocation getLocationSuggestion(int id) {
        establishedConnection();
        PreparedStatement preparedStatement = null;

        String sqlGetLocationSuggestion = "SELECT * FROM suggestion_locations WHERE ";


        return getLocationSuggestion(id);
    }

    @Override
    public HashMap<Integer, Person> getPeople() throws DataAccessException {
        return null;
    }


    @Override
    public HashMap<Integer, Person> getPeople(String stringMatch) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        Person person = null;
        String sqlGetPeople = "SELECT * FROM people WHERE ppl_raw_name = ?;";

        try {
            ResultSet resultSet = preparedStatement.executeQuery(sqlGetPeople);

            while (resultSet.next()) {

                person.setName(resultSet.getString(""));
                person.setDesc(resultSet.getString(""));
                person.setMail(resultSet.getString(""));
                person.setPicture(resultSet.getString(""));
                person.setRole(resultSet.getString(""));

            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute: " + e.getMessage());
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


    @Override
    public void createLocation(Location location) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlCreateLocation = "INSERT INTO locations (loc_name, loc_desc, loc_floor, loc_landmark, loc_latitude, loc_longitude) VALUES (?, ?, ?, ?, ?, ?)";
        //Execute a query.
        try {

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
                throw new DataAccessException("Failed to close connection/statement: " + e.getMessage());

            }
        }
    }


    @Override
    public void updateLocation(Location location) {

    }

    /*TODO: Needs further work later on.
        @Override
        public void createLocationSuggestion(ISuggestion suggestion) throws DataAccessException {
            establishedConnection();
            PreparedStatement preparedStatement = null;

            //Execute a query.
            try {
                String sqlCreateLocation = "INSERT INTO suggestion_locations (suggestion_loc_person_name, suggestion_loc_name, suggestion_loc_desc, suggestion_loc_floor, suggestion_loc_landmark, suggestion_loc_latitude, suggestion_loc_longitude, suggestion_loc_date)" +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                preparedStatement = establishedConnection().prepareStatement(sqlCreateLocation);
                preparedStatement.setString(1, suggestion.);
                preparedStatement.setString(2, suggestion.getDescription());
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
                    throw new DataAccessException("Failed to close connection/statement: " + e.getMessage());

                }
            }
        }
        }
    */
    @Override
    public void updateSuggestion(ISuggestion suggestion) throws DataAccessException {

    }

    @Override
    public void updateSuggestion(SuggestionLocation suggestionLocation) throws DataAccessException {

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
            throw new DataAccessException("Failed to create statement. Verify connection to database: " + e.getMessage());
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
    public void createLocationSuggestion(ISuggestion suggestion) throws DataAccessException {

    }


    @Override
    public void deleteLocationSuggestion(int suggestionID) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlDeleteSuggestion = "DELETE FROM suggestions WHERE suggestion_loc_id= ?";

        //Execute a query.
        try {
            preparedStatement = establishedConnection().prepareStatement(sqlDeleteSuggestion);
            preparedStatement.setInt(1, suggestionID);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to create statement. Verify connection to database: " + e.getMessage());
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
    public void createPerson(Person person) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String createPersonSQL = "INSERT INTO people (ppl_raw_name, ppl_mail, ppl_desc, ppl_picture, ppl_role, ppl_room) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = establishedConnection().prepareStatement(createPersonSQL);

            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getMail());
            preparedStatement.setString(3, person.getDesc());
            preparedStatement.setString(4, person.getPicture());
            preparedStatement.setString(5, person.getRole());
            preparedStatement.setString(6, person.getRoom());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to create statement. Verify connection to database: " + e.getMessage());
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
    public void updatePerson(Person person) {

    }

    @Override
    public void deletePerson(int personID) {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM people WHERE ppl_ID = ?";

        //Execute a query.
        try {
            preparedStatement = establishedConnection().prepareStatement(sql);
            preparedStatement.setInt(1, personID);
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
