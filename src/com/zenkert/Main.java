package com.zenkert;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    static private FolderOrganizer resetFolders(FolderOrganizer folders) {
        folders.resetFolders();
        return folders;
    }

    static private FolderOrganizer createFolderOrganizer() {
        Path dataPath = Paths.get("data").toAbsolutePath();
        System.out.println(dataPath);

        return new FolderOrganizer(dataPath, "files", "zips");
    }

    static private void downloadAndUnzipFile(FileOrganizer files) {
        FileProducer producer = new FileProducer(files);
        producer.download();
        producer.decompressGzip();
    }
    static private void manipulateFiles(FileOrganizer files, File movieInfo){
//        FileManipulator.convertTsvToCsv(files.getTsvFile().toFile(), files.getCsvFile().toFile());
        FileManipulator.filterCsv(files.getCsvFile().toFile(), movieInfo, files.getFilteredFile().toFile());
    }

    public static void main(String[] args) {
        FolderOrganizer folders = createFolderOrganizer();
//        resetFolders(folders);
        FileOrganizer ratingsFiles = new FileOrganizer(folders, "ratings.basics.tsv", "ratings.basics.csv" ,"ratings.basics.tsv.gz", "filtered.csv", "https://datasets.imdbws.com/title.ratings.tsv.gz" );
        FileOrganizer basicsFiles = new FileOrganizer(folders, "title.basics.tsv.gz", "title.basics.csv" ,"title.basics.tsv.gz", "filtered.csv", "https://datasets.imdbws.com/title.basics.tsv.gz" );
//        downloadAndUnzipFile(ratingsFiles);
        File movieInfo = basicsFiles.getCsvFile().toFile();
        manipulateFiles(ratingsFiles, movieInfo);
    }
}
