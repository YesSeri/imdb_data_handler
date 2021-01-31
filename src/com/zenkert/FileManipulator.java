package com.zenkert;

import java.io.*;
import java.util.Scanner;

public class FileManipulator {
    File tsvFile;
    File csvFile;

    public FileManipulator(FileOrganizer files) {
        this.tsvFile = files.getTsvFile().toFile();
        this.csvFile = files.getCsvFile().toFile();
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

    void convertTsvToCsv() {
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

    private String processLine(String line) {
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
}
