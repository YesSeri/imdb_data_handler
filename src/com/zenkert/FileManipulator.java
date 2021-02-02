package com.zenkert;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class FileManipulator {
    static void convertTsvToCsv(File tsvFile, File csvFile) {
        Scanner myReader = null;
        try {
            myReader = new Scanner(tsvFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try (FileWriter writer = new FileWriter(csvFile);
             BufferedWriter bw = new BufferedWriter(writer)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String csvLine = processLine(data);
                bw.write(csvLine);
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    static private String processLine(String line) {
        String[] newString = line.replace('"', '\'').split("\\t");
        String rLine = "";
        for (int i = 0; i < newString.length; i++) {
            rLine += "\"" + newString[i] + "\"";
            if (i < newString.length - 1) {
                rLine += ",";
            } else {
                rLine += "\n";
            }
        }
        return rLine;
    }

    static void filterCsv(File csvFile, File movieInfo, File outputFile) {
        Scanner myReader = null;
        try {
            myReader = new Scanner(csvFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try (FileWriter writer = new FileWriter(outputFile);
             BufferedWriter bw = new BufferedWriter(writer)) {
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                System.out.println("aaa");
                String data = myReader.nextLine();
                String[] row = data.replace("\"", "").split(",");
                String id = row[0];
                double rating = Double.parseDouble(row[1]);
                int votes = Integer.parseInt(row[2]);
                if (isMovie(id, movieInfo) == true && goodMovie(rating, votes) == true) {
                    bw.write(data + "\n");
                }
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    static private boolean goodMovie(double rating, int votes) {
        if (rating > 8.0 && votes > 1000) {
            return true;
        }
        return false;
    }

    static boolean isMovie(String id, File movieInfo) throws FileNotFoundException {
        return false;
    }

    static void countLinesInFile(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = 0;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            i++;
        }
        System.out.println("Number of lines in " + file + " are: " + i);
    }
}
