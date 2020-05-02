package com.microecom.customerservice.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.microecom.customerservice.model", "com.microecom.customerservice.http"})
@EnableJpaRepositories(basePackages = {"com.microecom.customerservice.model"})
@EntityScan(basePackages = {"com.microecom.customerservice.model"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
