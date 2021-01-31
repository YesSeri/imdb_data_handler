package com.zenkert;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    static private Organizer resetFolders(Organizer folders) {
        folders.resetFolders();
        return folders;
    }

    static private Organizer createFolderOrganizer() {
        Path dataPath = Paths.get("data").toAbsolutePath();
        return new Organizer(dataPath, "files", "zips", "title.ratings", "title.ratings.tsv.gz");
    }

    static private void downloadAndUnzipFile(Organizer folders) {

        FileProducer producer = new FileProducer(folders, "https://datasets.imdbws.com/title.ratings.tsv.gz");
        producer.download();
        producer.decompressGzip();
    }

    public static void main(String[] args) {
        Organizer folders = createFolderOrganizer();
//        resetFolders(folders);
//        downloadAndUnzipFile(folders);
        FileManipulator manipulator = new FileManipulator(folders);
        manipulator.readFile();
    }
}
