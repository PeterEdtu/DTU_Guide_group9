package database;//STEP 1. Import required packages

import java.sql.*;

public class Connector {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/distdb";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Mads";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        //STEP 2: Register JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Retrieval of JDBC driver failed: " + e.getMessage());
        }

        //STEP 3: Open a connection
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected successfully!");
        } catch (SQLException e) {
            System.out.println("Connection to database failed: " + e.getMessage());
        }

        //STEP 4: Execute a query
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql;
        sql = "SELECT tag_ID, tag_text FROM tags";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("tag_ID");
                String text = rs.getString("tag_text");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Text: " + text + "\n");
            }

        } catch (SQLException e) {
            System.out.println("SQL Command failed to execute: " + e.getMessage());
        }

        try {
            conn.close();
            System.out.println("Connection closed successfully!");
        } catch (SQLException e) {
            System.out.println("Connection failed to close properly: " + e.getMessage());
        }


    }
}