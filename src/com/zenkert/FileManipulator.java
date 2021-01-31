package com.zenkert;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class FileManipulator {
    File tsvFile;
    File csvFile;

    public FileManipulator(Organizer folders) {
//        this.tsvFile = folders.getTsvFile().toFile();
//        this.csvFile = folders.getCsvFile().toFile();
        this.tsvFile = new File("./data/files/test.tsv");
        this.tsvFile = new File("./data/files/test.csv");
        System.out.println(tsvFile);
    }

    void readFile() {
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
