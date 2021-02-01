package com.zenkert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class FileOrganizer {

    private Path zipFile;
    private Path tsvFile;
    private Path csvFile;
    private Path filteredFile;
    private URL imdbUrl;

    /**
     *
     *
     */
    public FileOrganizer(FolderOrganizer folders, String tsvName, String csvName, String zipFile, String filteredFile, String imdbUrl) {
        this.zipFile = folders.getZipPath().resolve(zipFile);
        this.tsvFile = folders.getFilePath().resolve(tsvName);
        this.csvFile = folders.getFilePath().resolve(csvName);
        this.filteredFile = folders.getFilePath().resolve(filteredFile);
        try {
            this.imdbUrl = new URL(imdbUrl);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * When you run this the mainPath folder will be deleted and recreated with an empty folder structure corresponding to the one you choose when creating object.
     */

    public Path getCsvFile() {
        return csvFile;
    }

    public Path getTsvFile() {
        return tsvFile;
    }

    public Path getZipFile() {
        return zipFile;
    }

    public Path getFilteredFile() {
        return filteredFile;
    }

    public URL getImdbUrl() {
        return imdbUrl;
    }

    @Override
    public String toString() {
        return "FileOrganizer{" +
                "zipFile=" + zipFile +
                ", tsvFile=" + tsvFile +
                ", csvFile=" + csvFile +
                '}';
    }
}
