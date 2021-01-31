package com.zenkert;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

/**
 * Downloads and unzips files. File to download is in the URL and output input zip info is in the Organizer
 */
public class FileProducer {
    private URL url;
    private Path zipFile;
    private Path outputFile;

    /**
     *
     * @param folders
     *  Uses this to save the zipFile and outputFile in the correct locations.
     * @param imdbUrl
     * An url where a .gz file is saved.
     *
     */
    public FileProducer(Organizer folders, String imdbUrl) {
        this.zipFile = folders.getZipFile();
        this.outputFile = folders.getTsvFile();
        try {
            this.url = new URL(imdbUrl);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * Will create a ReadableByteChannel and download the file to zipFile location.
     */
    void download() {
        try (
                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                FileOutputStream fos = new FileOutputStream(zipFile.toFile());
        ) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            System.out.println(zipFile + " has been downloaded.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    /**
     * Will unzip a .gz file to the location choosen in output file. I found the code for this here: https://mkyong.com/java/how-to-decompress-file-from-gzip-file/
     */
    void decompressGzip() {
        try (GZIPInputStream gis = new GZIPInputStream(
                new FileInputStream(zipFile.toFile()));
             FileOutputStream fos = new FileOutputStream(outputFile.toFile())) {

            // copy GZIPInputStream to FileOutputStream
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            System.out.println(zipFile + " has been extracted to " + outputFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public Path getOutputFile() {
        return outputFile;
    }
}
