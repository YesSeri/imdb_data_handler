package com.zenkert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class Organizer {

    private Path mainPath;
    private Path filePath;
    private Path zipPath;
    private Path zipFile;
    private Path tsvFile;
    private Path csvFile;

    /**
     * @param dataPath Mainfolder that you want to be created.
     * @param filePath A string that will be created as a subfolder in dataPath folder
     * @param zipPath  A string that will be created as a subfolder in dataPath folder
     */
    public Organizer(Path dataPath, String filePath, String zipPath, String fileName, String zipFile) {
        this.zipPath = dataPath.resolve(zipPath);
        this.filePath = dataPath.resolve(filePath);
        this.mainPath = dataPath;
        this.zipFile = this.zipPath.resolve(zipFile);
        this.tsvFile = this.filePath.resolve(fileName + ".tsv");
        this.csvFile = this.filePath.resolve(fileName + ".csv");
    }

    /**
     * When you run this the mainPath folder will be deleted and recreated with an empty folder structure corresponding to the one you choose when creating object.
     */

    void resetFolders() {
        deleteFolders();
        createFolders();
    }


    private void createFolders() {
        try {
            Files.createDirectories(filePath);
            Files.createDirectories(zipPath);
            System.out.println("Folders created at: " + mainPath.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFolders() {
        try {
            Files.walk(mainPath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            System.out.println("Folders deleted at: " + mainPath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println(mainPath.toAbsolutePath() + " doesn't exist. It has not been deleted.");
        }
    }

    public Path getCsvFile() {
        return csvFile;
    }

    public Path getTsvFile() {
        return tsvFile;
    }

    public Path getZipFile() {
        return zipFile;
    }

    public String toString() {
        return "FolderOrganizer{" +
                "mainPath=" + mainPath +
                ", filePath=" + filePath +
                ", zipPath=" + zipPath +
                '}';
    }
}
