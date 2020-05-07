package com.microecom.inventoryservice.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.microecom.inventoryservice.model", "com.microecom.inventoryservice.http"})
@EnableJpaRepositories(basePackages = {"com.microecom.inventoryservice.model"})
@EntityScan(basePackages = {"com.microecom.inventoryservice.model"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}