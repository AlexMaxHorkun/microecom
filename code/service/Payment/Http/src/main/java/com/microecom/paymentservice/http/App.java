package com.microecom.paymentservice.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.microecom.paymentservice.model", "com.microecom.paymentservice.http"})
@EnableJpaRepositories(basePackages = {"com.microecom.paymentservice.model"})
@EntityScan(basePackages = {"com.microecom.paymentservice.model"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}