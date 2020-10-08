package com.microecom.customerservice.model.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("com.microecom.customerservice.model")
@EnableJpaRepositories(basePackages = {"com.microecom.customerservice.model"})
@EntityScan(basePackages = {"com.microecom.customerservice.model"})
public class Config {
}
