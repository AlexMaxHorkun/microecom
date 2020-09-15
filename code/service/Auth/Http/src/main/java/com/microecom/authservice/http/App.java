package com.microecom.authservice.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.microecom.authservice.model", "com.microecom.authservice.http"})
@EnableJpaRepositories(basePackages = {"com.microecom.authservice.model"})
@EntityScan(basePackages = {"com.microecom.authservice.model"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
