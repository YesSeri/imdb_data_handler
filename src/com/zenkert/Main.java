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
    static private void manipulateFiles(FileOrganizer files){
        FileManipulator manipulator = new FileManipulator(files);
        manipulator.convertTsvToCsv();
    }

    public static void main(String[] args) {
        FolderOrganizer folders = createFolderOrganizer();
//        resetFolders(folders);
        FileOrganizer basicFiles = new FileOrganizer(folders, "title.basics.tsv", "title.basics.csv" ,"title.basics.tsv.gz", "https://datasets.imdbws.com/title.basics.tsv.gz" );
        FileOrganizer ratingsFiles = new FileOrganizer(folders, "ratings.basics.tsv", "ratings.basics.csv" ,"ratings.basics.tsv.gz", "https://datasets.imdbws.com/title.ratings.tsv.gz" );
        downloadAndUnzipFile(basicFiles);
        downloadAndUnzipFile(ratingsFiles);
//        manipulateFiles(basicFiles);
//        manipulateFiles(ratingsFiles);
//        Path p1 = Paths.get("test", "test2.csv").toAbsolutePath();
//        Path p2 = Paths.get("data", "files", "test2.csv").toAbsolutePath();
//        FileManipulator.mergeCsvs(p1.toFile(), p2.toFile(), folders.getFilePath().resolve("merged.csv").toFile());
//        FileManipulator.mergeCsvs(ratingsFiles.getCsvFile().toFile(), basicFiles.getCsvFile().toFile(), folders.getFilePath().resolve("merged.csv").toFile());
        FileManipulator.countLinesInFile(ratingsFiles.getCsvFile().toFile());
    }
}
