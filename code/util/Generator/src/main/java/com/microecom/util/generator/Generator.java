package com.microecom.util.generator;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Generator {
    private static AtomicInteger created = new AtomicInteger();

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

    public static void processResponse(HttpResponse<String> response) {
        if (response.statusCode() == 201) {
            System.out.println(String.format("Created user #%d",
                    created.addAndGet(1)
            ))
            ;
        } else {
            System.out.println(String.format("Failed to create: %s", response.body()));
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
        var futures = new HashSet<CompletableFuture<HttpResponse<String>>>();

        for (int i = 0; i < count; i++) {
            var id = i + start;
            System.out.println(String.format("Trying to created user #%d", id));
            var requestObject = new NewCustomerRequest(
                    String.format("test%d@test.com", id),
                    "test",
                    "test",
                    String.format("test_%d", id),
                    "12345aBc"
            );
            var request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(requestObject)))
                    .header("Content-Type", "application/json")
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            var client = HttpClient.newHttpClient();

            var future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            future.thenAcceptAsync(Generator::processResponse);
            futures.add(future);
        }

        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(String.format("Created %d number of customers", created.get()));
    }
}
