package com.microecom.customerservice.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.microecom.customerservice.model")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
