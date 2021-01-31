package com.zenkert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileProducer {
    URL url;

    public FileProducer(String url) {
        try {
            this.url = new URL(url);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    void download() {
        try {
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream("title.ratings.tsv.gz");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileProducer a = new FileProducer("https://datasets.imdbws.com/title.ratings.tsv.gz");
    }
}
