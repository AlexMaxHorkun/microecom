package com.microecom.catalogservice.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.microecom.catalogservice.model", "com.microecom.catalogservice.event"})
@EnableJpaRepositories(basePackages = {"com.microecom.catalogservice.model"})
@EntityScan(basePackages = {"com.microecom.catalogservice.model"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
