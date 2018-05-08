package database.connector;

import controllers.exceptions.DataAccessException;
import data.*;
import database.interfaces.IConnector;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("SqlNoDataSourceInspection")
public class Connector implements IConnector {

    //JDBC driver name, and database URL:
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    @SuppressWarnings("SpellCheckingInspection")
    //private static final String DB_URL = "jdbc:mysql://localhost/distdb?useSSL=false";
    private static final String DB_URL = "jdbc:mysql://arvid-langsoe.dk:3306/distdb?useSSL=false";


    // Database credentials:
    //private static final String USER = "root";
    private static final String USER = "remoteDist";
    @SuppressWarnings("SpellCheckingInspection")
    //private static final String PASS = "Mads";
    private static final String PASS = "qwerty";

    private Connection establishedConnection() {
        Connection conn = null;
        //     System.out.println("Connecting to database...");
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //          System.out.println("Connected successfully!");
        } catch (SQLException e) {
            System.out.println("Connection to database failed: " + e.getMessage() + "\n");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
        PreparedStatement preparedStatement = null;
        Location location;
        HashMap<String, Location> tempHashmap = new HashMap<>();

        String sqlGetLocations = "SELECT * FROM locations WHERE loc_name LIKE ?;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetLocations);
            preparedStatement.setString(1, "%" + stringMatch + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                location = new Location();
                location.setName(resultSet.getString("loc_name"));
                location.setDescription(resultSet.getString("loc_desc"));
                location.setFloor(resultSet.getInt("loc_floor"));
                location.setLandmark(resultSet.getString("loc_landmark"));
                location.setLatitude(resultSet.getDouble("loc_latitude"));
                location.setLongitude(resultSet.getDouble("loc_longitude"));

                location.setTags(getTagsFromLocation(location.getName()));

                tempHashmap.put(location.getName(), location);
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Retrieved information from locations!");
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
        return tempHashmap;
    }

    /**
     * @param stringMatch Input to search for, in the 'people' table.
     * @return Returns getPeople hashmap.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public HashMap<Integer, Person> getPeople(String stringMatch) throws DataAccessException {
        PreparedStatement preparedStatement = null;
        Person person;
        Location location;
        HashMap<Integer, Person> tempHashmap = new HashMap<>();


        String sqlGetPeople = "SELECT * FROM people_locations WHERE ppl_raw_name LIKE ?;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetPeople);
            preparedStatement.setString(1, "%" + stringMatch + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                person = new Person();
                location = new Location();

                person.setId(resultSet.getInt("ppl_ID"));
                person.setName(resultSet.getString("ppl_raw_name"));
                person.setMail(resultSet.getString("ppl_mail"));
                person.setDescription(resultSet.getString("ppl_desc"));
                person.setPicture(resultSet.getString("ppl_picture"));
                person.setRole(resultSet.getString("ppl_role"));


                location.setName(resultSet.getString("loc_name"));
                location.setDescription(resultSet.getString("loc_desc"));
                location.setFloor(resultSet.getInt("loc_floor"));
                location.setLandmark(resultSet.getString("loc_landmark"));
                location.setLatitude(resultSet.getDouble("loc_latitude"));
                location.setLongitude(resultSet.getDouble("loc_longitude"));
                location.setTags(getTagsFromLocation(location.getName()));

                person.setLocation(location);

                tempHashmap.put(person.getId(), person);
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Retrieved information from people_locations!");
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
        return tempHashmap;

    }

    public Person getPeople(int id) throws DataAccessException {
        PreparedStatement preparedStatement = null;
        Person person = new Person();
        Location location = new Location();

        String sqlGetPeople = "SELECT * FROM people_locations WHERE ppl_ID = ?;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetPeople);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {

                person.setId(resultSet.getInt("ppl_ID"));
                person.setName(resultSet.getString("ppl_raw_name"));
                person.setMail(resultSet.getString("ppl_mail"));
                person.setDescription(resultSet.getString("ppl_desc"));
                person.setPicture(resultSet.getString("ppl_picture"));
                person.setRole(resultSet.getString("ppl_role"));


                location.setName(resultSet.getString("loc_name"));
                location.setDescription(resultSet.getString("loc_desc"));
                location.setFloor(resultSet.getInt("loc_floor"));
                location.setLandmark(resultSet.getString("loc_landmark"));
                location.setLatitude(resultSet.getDouble("loc_latitude"));
                location.setLongitude(resultSet.getDouble("loc_longitude"));
                try {
                    location.setTags(getTagsFromLocation(location.getName()));
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }

                person.setLocation(location);

            }

                return person;

        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Retrieved information from people_locations!");
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }

        }

    }

    /**
     * @return Returns an array list of all admins in the system.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public ArrayList<String> getAdmins() throws DataAccessException {
        PreparedStatement preparedStatement = null;
        String sqlGetAdmins = "SELECT * FROM people_admins;";
        ArrayList<String> tempArrayList = new ArrayList<>();


        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetAdmins);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tempArrayList.add(resultSet.getString("admin_name"));
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Retrieved information from people_admins!");
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
        return tempArrayList;
    }

    private ArrayList<String> getTagsFromLocation(String name) throws DataAccessException {
        PreparedStatement preparedStatement = null;
        ArrayList<String> tempArrayList = new ArrayList<>();

        String sqlGetTags = "SELECT * FROM tagslocations WHERE loc_name = ?;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetTags);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tempArrayList.add(resultSet.getString("tag_text"));
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
        return tempArrayList;
    }

    private ArrayList<String> getTagsFromSuggestionLocation(int id) throws DataAccessException {
        PreparedStatement preparedStatement = null;
        ArrayList<String> tempArrayList = new ArrayList<>();

        String sqlGetTags = "SELECT * FROM tagssuggestionlocations WHERE suggestion_loc_ID = ?;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetTags);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tempArrayList.add(resultSet.getString("tag_text"));
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
        return tempArrayList;
    }

    /**
     * @return Returns an array list of all tags in the system.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public ArrayList<Tag> getTags() throws DataAccessException {
        PreparedStatement preparedStatement = null;
        Tag tagTemp;
        ArrayList<Tag> tempArrayList = new ArrayList<>();

        String sqlGetTags = "SELECT * FROM tags;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetTags);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tagTemp = new Tag();

                tagTemp.setId(resultSet.getInt("tag_ID"));
                tagTemp.setTagText(resultSet.getString("tag_text"));

                tempArrayList.add(tagTemp);
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Retrieved information from tags");
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
        return tempArrayList;
    }

    /**
     * @param adminName Input to add to the admin table in database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void createAdmin(String adminName) throws DataAccessException {
        PreparedStatement preparedStatement = null;
        String sqlCreateAdmin = "INSERT INTO people_admins (admin_name) VALUES (?);";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlCreateAdmin);
            preparedStatement.setString(1, adminName);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Inserted " + adminName + " into people_admins table");
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
        PreparedStatement preparedStatement = null;
        String sqlDeleteAdmin = "DELETE FROM people_admins WHERE admin_name = ?;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlDeleteAdmin);
            preparedStatement.setString(1, adminName);
            preparedStatement.executeUpdate();
            System.out.println("Deleted an admin from admin table. Removed admin name: " + adminName);
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Deleted " + adminName + " from people_admins table");
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
    }

    /**
     * @return Returns an array list of all location suggestions from the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public ArrayList<SuggestionLocation> getLocationSuggestions() throws DataAccessException {

        PreparedStatement preparedStatement = null;
        SuggestionLocation suggestionLocation;
        ArrayList<SuggestionLocation> tempArrayList = new ArrayList<>();


        String sqlGetLocationSuggestions = "SELECT * FROM suggestion_locations;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetLocationSuggestions);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                suggestionLocation = new SuggestionLocation();

                suggestionLocation.setSuggestionID(resultSet.getInt("suggestion_loc_ID"));
                suggestionLocation.setAuthor(resultSet.getString("suggestion_loc_author"));
                suggestionLocation.setName(resultSet.getString("suggestion_loc_name"));
                suggestionLocation.setDescription(resultSet.getString("suggestion_loc_desc"));
                suggestionLocation.setFloor(resultSet.getInt("suggestion_loc_floor"));
                suggestionLocation.setLandmark(resultSet.getString("suggestion_loc_landmark"));
                suggestionLocation.setLatitude(resultSet.getDouble("suggestion_loc_latitude"));
                suggestionLocation.setLongitude(resultSet.getDouble("suggestion_loc_longitude"));
                suggestionLocation.setDate(resultSet.getDate("suggestion_loc_date"));

                suggestionLocation.setTags(getTagsFromSuggestionLocation(suggestionLocation.getSuggestionID()));

                tempArrayList.add(suggestionLocation);
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Retrieved information from suggestion_locations!");
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
        return tempArrayList;
    }

    /**
     * @return Return an array list of all people suggestions from the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public ArrayList<SuggestionPerson> getPeopleSuggestions() throws DataAccessException {
        PreparedStatement preparedStatement = null;
        SuggestionPerson suggestionPerson;
        ArrayList<SuggestionPerson> tempArrayList = new ArrayList<>();
        String sqlGetPeopleSuggestions = "SELECT * FROM suggestion_people;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetPeopleSuggestions);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                suggestionPerson = new SuggestionPerson();

                suggestionPerson.setId(resultSet.getInt("ppl_ID"));
                suggestionPerson.setSuggestionID(resultSet.getInt("suggestion_ppl_ID"));
                suggestionPerson.setAuthor(resultSet.getString("suggestion_ppl_author"));
                suggestionPerson.setName(resultSet.getString("suggestion_ppl_name"));
                suggestionPerson.setMail(resultSet.getString("suggestion_ppl_mail"));
                suggestionPerson.setDescription(resultSet.getString("suggestion_ppl_desc"));
                suggestionPerson.setPicture(resultSet.getString("suggestion_ppl_picture"));
                suggestionPerson.setRole(resultSet.getString("suggestion_ppl_role"));
                suggestionPerson.setRoom(resultSet.getString("suggestion_ppl_room"));
                suggestionPerson.setDate(resultSet.getDate("suggestion_ppl_date"));

                tempArrayList.add(suggestionPerson);
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Retrieved information from suggestion_people!");
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
        return tempArrayList;
    }

    /**
     * @param id Integer input to search for in the location suggestion table in the database
     * @return Return a suggestionLocation with a specific ID.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public SuggestionLocation getLocationSuggestion(int id) throws DataAccessException {
        PreparedStatement preparedStatement = null;
        SuggestionLocation suggestionLocation;
        String sqlGetLocationSuggestion = "SELECT * FROM suggestion_locations WHERE suggestion_loc_ID = ?;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetLocationSuggestion);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            suggestionLocation = new SuggestionLocation();
            if (resultSet.next()) {
                suggestionLocation.setSuggestionID(resultSet.getInt("suggestion_loc_ID"));
                suggestionLocation.setAuthor(resultSet.getString("suggestion_loc_author"));
                suggestionLocation.setName(resultSet.getString("suggestion_loc_name"));
                suggestionLocation.setDescription(resultSet.getString("suggestion_loc_desc"));
                suggestionLocation.setFloor(resultSet.getInt("suggestion_loc_floor"));
                suggestionLocation.setLandmark(resultSet.getString("suggestion_loc_landmark"));
                suggestionLocation.setLatitude(resultSet.getDouble("suggestion_loc_latitude"));
                suggestionLocation.setLongitude(resultSet.getDouble("suggestion_loc_longitude"));
                suggestionLocation.setDate(resultSet.getDate("suggestion_loc_date"));

                suggestionLocation.setTags(getTagsFromSuggestionLocation(suggestionLocation.getSuggestionID()));


                System.out.println("Retrieved information about " + suggestionLocation.getName() + " from suggestion_locations!");
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
        PreparedStatement preparedStatement = null;
        SuggestionPerson suggestionPerson;
        String sqlGetPeopleSuggestion = "SELECT * FROM suggestion_people WHERE suggestion_ppl_ID = ?;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlGetPeopleSuggestion);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            suggestionPerson = new SuggestionPerson();

            if (resultSet.next()) {

                suggestionPerson.setSuggestionID(resultSet.getInt("suggestion_ppl_ID"));
                suggestionPerson.setAuthor(resultSet.getString("suggestion_ppl_author"));
                suggestionPerson.setName(resultSet.getString("suggestion_ppl_name"));
                suggestionPerson.setMail(resultSet.getString("suggestion_ppl_mail"));
                suggestionPerson.setDescription(resultSet.getString("suggestion_ppl_desc"));
                suggestionPerson.setPicture(resultSet.getString("suggestion_ppl_picture"));
                suggestionPerson.setRole(resultSet.getString("suggestion_ppl_role"));
                suggestionPerson.setRoom(resultSet.getString("suggestion_ppl_room"));
                suggestionPerson.setDate(resultSet.getDate("suggestion_ppl_date"));

                System.out.println("Retrieved information about " + suggestionPerson.getName() + " from suggestion_people !");

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

    private int getTagID(String name) throws DataAccessException {
        ArrayList<Tag> tags = getTags();

        for (Tag tag : tags) {
            if (tag.getTagText().equals(name)) {
                return tag.getId();

            }
        }

        return -1;
    }

    /*Does not work properly at the current time (4th of may)*/
    private void updateTagsForLocationSuggestion(int id, ArrayList<String> newTags) throws DataAccessException {
        if (newTags == null) {
            return;
        }

        for (String tag : newTags) {

            PreparedStatement preparedStatement = null;
            String sqlUpdateLocation = "INSERT INTO suggestion_tags (suggestion_loc_ID, suggestion_tag) VALUES (?, ?);";

            try {
                preparedStatement = establishedConnection().prepareStatement(sqlUpdateLocation);
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, getTagID(tag));

                //getTagID(tag)

                preparedStatement.executeUpdate();
                System.out.println("Updated location tags for: " + id);
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
    }

    private void resetTagsForLocation(String name) throws DataAccessException {

        PreparedStatement preparedStatement = null;
        String sqlUpdateLocation = "DELETE FROM room_tags WHERE loc_name = ?;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlUpdateLocation);
            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();
            System.out.println("Removed location tags for: " + name);
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

    private void updateTagsForLocation(String name, ArrayList<String> newTags) throws DataAccessException {
        if (newTags == null) {
            return;
        }

        resetTagsForLocation(name);

        for (String tag : newTags) {

            PreparedStatement preparedStatement = null;
            String sqlUpdateLocation = "INSERT INTO room_tags (loc_name, tag_ID) VALUES (?, ?);";

            try {
                preparedStatement = establishedConnection().prepareStatement(sqlUpdateLocation);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, getTagID(tag));

                //getTagID(tag)

                preparedStatement.executeUpdate();
                System.out.println("Updated location tags for: " + name);
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
    }

    /**
     * @param location Input inserted, to be changed.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override //room_tags
    public void updateLocation(Location location) throws DataAccessException {
        PreparedStatement preparedStatement = null;
        String sqlUpdateLocation = "UPDATE locations SET loc_desc = ?, loc_floor = ?, loc_landmark = ?, loc_latitude = ?, loc_longitude = ? WHERE loc_name = ?;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlUpdateLocation);
            preparedStatement.setString(1, location.getDescription());
            preparedStatement.setInt(2, location.getFloor());
            preparedStatement.setString(3, location.getLandmark());
            preparedStatement.setDouble(4, location.getLatitude());
            preparedStatement.setDouble(5, location.getLongitude());
            preparedStatement.setString(6, location.getName());

            updateTagsForLocation(location.getName(), location.getTags());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Updated location data for: " + location.getName() + ("!"));
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
        PreparedStatement preparedStatement = null;
        String sqlDeleteLocation = "DELETE FROM locations WHERE loc_name = ?;";

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
                System.out.println("Deleted location, " + locationName + ", from locations!");
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

            java.util.Date ourDate = suggestionLocation.getDate();
            LocalDate local = ourDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Date sqlDate = Date.valueOf(local);

            preparedStatement.setDate(8, sqlDate);

            preparedStatement.executeUpdate();

            ArrayList<SuggestionLocation> arrayList = getLocationSuggestions();
            int index = -1;

            preparedStatement = establishedConnection().prepareStatement("SELECT MAX(suggestion_loc_ID) AS new_id FROM suggestion_locations;");
            ResultSet resultSet =preparedStatement.executeQuery();


            resultSet.first();
            index = resultSet.getInt("new_id");

            updateTagsForLocationSuggestion(index, suggestionLocation.getTags());

        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Created " + suggestionLocation.getName() + " into suggestion_locations!");
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
    @SuppressWarnings("Duplicates")
    public void updateLocationSuggestion(SuggestionLocation suggestionLocation) throws DataAccessException {
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
            preparedStatement.setInt(9, suggestionLocation.getSuggestionID());


            java.util.Date ourDate = suggestionLocation.getDate();
            LocalDate local = ourDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Date sqlDate = Date.valueOf(local);

            preparedStatement.setDate(8, sqlDate);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Updated " + suggestionLocation.getName() + " in suggestion_locations!");

            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
    }

    /**
     * @param id Inserted suggestion id input to be deleted from the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void deleteLocationSuggestion(int id) throws DataAccessException {
        PreparedStatement preparedStatement = null;
        String sqlDeleteLocationSuggestion = "DELETE FROM suggestion_locations WHERE suggestion_loc_ID = ?;";

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
                System.out.println("Deleted location id: " + id + ", from suggestion_locations!");
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

            updateTagsForLocation(location.getName(), location.getTags());

        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Created " + location.getName() + " in locations!");

            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }

    }

    /**
     * @param person Inserted object to be updated in the people table, in the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void updatePerson(Person person) throws DataAccessException {
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
                System.out.println("Updated " + person.getName() + " in people!");
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
    }


    /**
     * @param id Inserted id to be deleted from the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void deletePerson(int id) throws DataAccessException {
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
                System.out.println("Deleted " + id + " from people!");

            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
    }

    /**
     * @param suggestionPerson Object to be created in the suggestion_people table, in the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void createPeopleSuggestion(SuggestionPerson suggestionPerson) throws DataAccessException {
        PreparedStatement preparedStatement = null;
        String sqlCreatePeopleSuggestion = "INSERT INTO suggestion_people (suggestion_ppl_author, ppl_ID, suggestion_ppl_name" +
                ", suggestion_ppl_mail, suggestion_ppl_desc, suggestion_ppl_picture, suggestion_ppl_role, " +
                "suggestion_ppl_room, suggestion_ppl_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlCreatePeopleSuggestion);
            preparedStatement.setString(1, suggestionPerson.getAuthor());
            preparedStatement.setInt(2, 0);
            preparedStatement.setString(3, suggestionPerson.getName());
            preparedStatement.setString(4, suggestionPerson.getMail());
            preparedStatement.setString(5, suggestionPerson.getDescription());
            preparedStatement.setString(6, suggestionPerson.getPicture());
            preparedStatement.setString(7, suggestionPerson.getRole());
            preparedStatement.setString(8, suggestionPerson.getRoom());

            java.util.Date ourDate = suggestionPerson.getDate();
            LocalDate local = ourDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Date sqlDate = Date.valueOf(local);

            preparedStatement.setDate(9, sqlDate);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Created " + suggestionPerson.getName() + " in suggestion_people !");
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
    }

    /**
     * @param suggestionPerson Object to be updated in the suggestion_people table, in the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void updatePeopleSuggestion(SuggestionPerson suggestionPerson) throws DataAccessException {
        establishedConnection();
        PreparedStatement preparedStatement = null;
        String sqlUpdateLocationSuggestion = "UPDATE suggestion_people SET suggestion_ppl_author = ?, suggestion_ppl_name = ?, " +
                "suggestion_ppl_mail = ?, suggestion_ppl_desc = ?, suggestion_ppl_picture = ?, suggestion_ppl_role = ?, " +
                "suggestion_ppl_room = ?, suggestion_ppl_date = ? WHERE suggestion_ppl_ID = ?";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlUpdateLocationSuggestion);
            preparedStatement.setString(1, suggestionPerson.getAuthor());
            preparedStatement.setString(2, suggestionPerson.getName());
            preparedStatement.setString(3, suggestionPerson.getMail());
            preparedStatement.setString(4, suggestionPerson.getDescription());
            preparedStatement.setString(5, suggestionPerson.getPicture());
            preparedStatement.setString(6, suggestionPerson.getRole());
            preparedStatement.setString(7, suggestionPerson.getRoom());

            preparedStatement.setInt(9, suggestionPerson.getSuggestionID());


            java.util.Date ourDate = suggestionPerson.getDate();
            LocalDate local = ourDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Date sqlDate = Date.valueOf(local);

            preparedStatement.setDate(8, sqlDate);

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
     * @param id Inserted id to be removed from the suggestion_people table in the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void deletePeopleSuggestion(int id) throws DataAccessException {
        PreparedStatement preparedStatement = null;
        String sqlDeletePeopleSuggestion = "DELETE FROM suggestion_people WHERE suggestion_ppl_ID = ?;";

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
                System.out.println("Deleted people suggestion: " + id + ", from suggestion_people!");
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
    }


    /**
     * @param person The object inserted to be added to the people table, in the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void createPerson(Person person) throws DataAccessException {
        PreparedStatement preparedStatement = null;
        String sqlCreatePerson = "INSERT INTO people (ppl_raw_name, ppl_mail, ppl_desc, ppl_picture, ppl_role," +
                " ppl_room) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlCreatePerson);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getMail());
            preparedStatement.setString(3, person.getDescription());
            preparedStatement.setString(4, person.getPicture());
            preparedStatement.setString(5, person.getRole());
            preparedStatement.setString(6, person.getRoom());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Inserted a person, " + person.getName() + ", in people!");

            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
    }

    /**
     * @param tag The object inserted to be added to the tag table, in the database.
     * @throws DataAccessException Exception thrown in case a SQL command fails.
     */
    @Override
    public void createTag(Tag tag) throws DataAccessException {
        PreparedStatement preparedStatement = null;
        String sqlCreateTag = "INSERT INTO tags (tag_text) VALUES (?);";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlCreateTag);
            preparedStatement.setString(1, tag.getTagText());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("SQL command failed to execute:" + e.getMessage());
        }
        //Close connection and statement.
        finally {
            try {
                preparedStatement.close();
                establishedConnection().close();
                System.out.println("Inserted tag, " + tag.getTagText() + ", into tags!");
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void deleteTag(int id) throws DataAccessException {
        PreparedStatement preparedStatement = null;
        String sqlDeleteTag = "DELETE FROM tags WHERE tag_ID = ?;";

        try {
            preparedStatement = establishedConnection().prepareStatement(sqlDeleteTag);
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
                System.out.println("Deleted tag id: " + id + " from tags!");
            } catch (SQLException e) {
                System.out.println("Failed to close connection/statement: " + e.getMessage());
            }
        }

    }
}