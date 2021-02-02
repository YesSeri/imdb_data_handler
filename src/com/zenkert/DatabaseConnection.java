package com.zenkert;

import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class DatabaseConnection {
    private Connection con;
    private Statement statement;
    private ResultSet result;
    private File titleFile;
    File ratingsFile;

    /**
     * This creates a connection to localhost database and creates a new db called imdb_info. It deletes the old one if there is one.
     */
    public DatabaseConnection(File ratingsFile, File titleFile) {
        this.ratingsFile = ratingsFile;
        this.titleFile = titleFile;
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

    void resetDatabase() {
        try {
            createDatabase();
            createTables();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createDatabase() throws SQLException {
        Statement s = con.createStatement();
        s.executeUpdate("DROP DATABASE IF EXISTS imdb_info");
        s.executeUpdate("CREATE DATABASE imdb_info");
        s.executeUpdate("USE imdb_info");
    }

    void insertRatings() throws SQLException, FileNotFoundException {
//        ratingsFile = new File("test/testRatings.csv");
        Scanner scanner = new Scanner(ratingsFile);
        int i = 0;
        int count = 0;
        scanner.nextLine();
        PreparedStatement pstmt = null;
        while (scanner.hasNextLine()) {
            i++;
            String data = scanner.nextLine();
            String[] row = data.replace("\"", "").split(",");

            if (isGoodMovie(row)) {
                pstmt = createPreparedStatementRatings(row);
                pstmt.addBatch();
                count++;
                if (count > 10000) {
                    System.out.println("Batch inserted");
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                    count = 0;
                }
            }

            if (i % 10000 == 0) {
                System.out.println("Rating record number: " + i);
            }
        }
        pstmt.close();
    }

    private boolean isGoodMovie(String[] row) {
        double rating = Double.parseDouble(row[1]);
        int numVotes = Integer.parseInt(row[2]);
        if (numVotes > 1000 & rating > 8.0) {
            return true;
        }
        return false;


    }

    private PreparedStatement createPreparedStatementRatings(String[] row) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement("INSERT INTO ratings (tconst, averageRating, numVotes)\n" +
                "VALUES (?, ?, ?);");

        pstmt.setString(1, row[0]);
        pstmt.setFloat(2, Float.parseFloat(row[1]));
        pstmt.setInt(3, Integer.parseInt(row[2]));
        return pstmt;
    }

    void insertTitle() throws SQLException, FileNotFoundException {
        Scanner scanner = null;
//        titleFile = new File("test/testTitle.csv");
        scanner = new Scanner(titleFile);
        scanner.nextLine();
        int j = 0;
        int count = 0;
        PreparedStatement pstmt = null;
        while (scanner.hasNextLine()) {
            j++;
            String data = scanner.nextLine();
            String[] row = data.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int i = 0; i < row.length; i++) {
                row[i] = row[i].replace("\"", "");
            }
            if (isMovie(row[1])) {
                pstmt = createPreparedStatment(row);
                pstmt.addBatch();
                count++;
                if (count > 10000) {
                    System.out.println("Batch inserted");
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                    count = 0;
                }
            }

            if (j % 10000 == 0) {
                System.out.println("Rating record number: " + j);
            }
        }
        pstmt.executeBatch();
    }

    private boolean isMovie(String titleType) {
        if (titleType.equals("movie")) {
            return true;
        }
        return false;
    }

    private PreparedStatement createPreparedStatment(String[] row) throws SQLException {
        // This oneliner splits a string only if the comma is not between two "".

        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement("INSERT INTO title " +
                "(tconst, titleType, primaryTitle, originalTitle, isAdult, startYear, endYear, runtimeMinutes, genres)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");

        pstmt.setString(1, row[0]);
        pstmt.setString(2, row[1]);
        pstmt.setString(3, row[2]);
        pstmt.setString(4, row[3]);
        pstmt.setString(5, row[4]);
        try {
            pstmt.setInt(6, Integer.parseInt(row[5]));
        } catch (NumberFormatException e) {
            pstmt.setNull(6, Types.NULL);
        }
        try {
            pstmt.setInt(7, Integer.parseInt(row[6]));
        } catch (NumberFormatException e) {
            pstmt.setNull(7, Types.NULL);
        }
        try {
            pstmt.setInt(8, Integer.parseInt(row[7]));
        } catch (NumberFormatException e) {
            pstmt.setNull(8, Types.NULL);
        }
        pstmt.setString(9, row[8]);

        return pstmt;
    }


    void createTables() throws SQLException {
        Statement s = null;
        s = con.createStatement();
        String ratingsTableQuery = "CREATE TABLE ratings (\n" +
                "id INT(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
                "tconst VARCHAR(30) NOT NULL,\n" +
                "averageRating FLOAT NOT NULL,\n" +
                "numVotes INT(11)\n" +
                ")";
        String titleTableQuery = "CREATE TABLE title (\n" +
                "id INT(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
                "tconst VARCHAR(30) NOT NULL,\n" +
                "titleType VARCHAR(30),\n" +
                "primaryTitle VARCHAR(255), \n" +
                "originalTitle VARCHAR(255),\n" +
                "isAdult VARCHAR(30),\n" +
                "startYear INT(6),\n" +
                "endYear INT(6),\n" +
                "runtimeMinutes INT(6),\n" +
                "genres VARCHAR(255)\n" +
                ")";
        s.executeUpdate(ratingsTableQuery);
        s.executeUpdate(titleTableQuery);
    }

    public static void main(String[] args) {
//        DatabaseConnection d = new DatabaseConnection();
    }

}
