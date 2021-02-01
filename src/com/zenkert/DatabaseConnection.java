package com.zenkert;

import java.sql.*;

public class DatabaseConnection {
    private Connection con;
    private Statement statement;
    private ResultSet result;

    /**
     * This creates a connection to localhost database and creates a new db called imdb_info. It deletes the old one if there is one.
     */
    public DatabaseConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", ""); //code stucks here and after some minutes it is throwing an exception
            System.out.println("Connected");//this is never executed.
            createDatabase();
            createTables();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void insertRow(String[] arr) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("UPDATE EMPLOYEES SET SALARY = ? WHERE ID = ?");
    }

    private void createDatabase() throws SQLException {
            Statement s = con.createStatement();
            s.executeUpdate("DROP DATABASE IF EXISTS imdb_info");
            s.executeUpdate("CREATE DATABASE imdb_info");
            s.executeUpdate("USE imdb_info");
    }

    void createTables() {
        Statement s = null;
        try {
            s = con.createStatement();
            String myTableName = "CREATE TABLE ratings (\n" +
                    "id INT(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
                    "tconst VARCHAR(30) NOT NULL,\n" +
                    "averageRating FLOAT NOT NULL,\n" +
                    "numVotes INT(11)\n" +
                    ")";
            s.executeUpdate(myTableName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            s = con.createStatement();
            String myTableName = "CREATE TABLE title (\n" +
                    "id INT(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
                    "tconst VARCHAR(30) NOT NULL,\n" +
                    "titleType VARCHAR(30) NOT NULL,\n" +
                    "primaryTitle VARCHAR(255),\n" +
                    "originalTitle VARCHAR(255),\n" +
                    "isAdult VARCHAR(30),\n" +
                    "startYear INT(6),\n" +
                    "endYear VARCHAR(6),\n" +
                    "runtimeMinutes INT(6),\n" +
                    "genres INT(6)\n" +
                    ")";
            s.executeUpdate(myTableName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DatabaseConnection d = new DatabaseConnection();
    }

}
