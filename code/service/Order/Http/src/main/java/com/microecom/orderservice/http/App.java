package com.microecom.orderservice.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.microecom.orderservice.model", "com.microecom.orderservice.http"})
@EnableJpaRepositories(basePackages = {"com.microecom.orderservice.model"})
@EntityScan(basePackages = {"com.microecom.orderservice.model"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
