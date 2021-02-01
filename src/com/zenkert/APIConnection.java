package com.zenkert;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class APIConnection {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public APIConnection() {
    }

    void APIRequest() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


//        Gson gson = new Gson();
//        String json = gson.fromJson(response.body());
//        System.out.println(json);
    }

    public static void main(String[] args) {
        APIConnection conn = new APIConnection();
        try {
            conn.APIRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
