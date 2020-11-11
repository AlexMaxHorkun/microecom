package com.microecom.util.generator;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Generator {
    private static int created = 0;

    private static Gson gson = new Gson();

    public static class NewCustomerRequest {
        private String email;

        private String firstName;

        private String lastName;

        private String login;

        private String password;

        public NewCustomerRequest(String email, String firstName, String lastName, String login, String password) {
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.login = login;
            this.password = password;
        }

        public NewCustomerRequest() {
        }

        public String getEmail() {
            return email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }

    private static final class RequestSender implements Runnable {
        private final int id;

        private final URI url;

        public RequestSender(int id, URI url) {
            this.id = id;
            this.url = url;
        }

        @Override
        public void run() {
            var requestObject = new NewCustomerRequest(
                    String.format("test%d@test.com", id),
                    "test",
                    "test",
                    String.format("test_%d", id),
                    "password"
            );
            var request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(requestObject)))
                    .header("Content-Type", "application/json")
                    .build();

            var client = HttpClient.newHttpClient();

            try {
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 201) {
                    created++;
                }
            } catch (Exception ex) {
                //Whatevs
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 4) {
            throw new IllegalArgumentException("Wrong number of arguments");
        }
        var host = args[0];
        var port = args[1];
        var count = Integer.parseInt(args[2]);
        var start = Integer.parseInt(args[3]);
        var url = URI.create(String.format("http://%s:%s/rest/V1/customer", host, port));

        var executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < count; i++) {
            executor.submit(new RequestSender(i + start, url));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException ex) {
            throw new RuntimeException("What?");
        }
        System.out.println(String.format("Created %d number of customers", created));
    }
}
