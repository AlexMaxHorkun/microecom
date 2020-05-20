package com.microecom.orderservice.model.data;

import java.util.Optional;

public class OrdersCriteria {
    private final String customerId;

    public OrdersCriteria(String customerId) {
        this.customerId = customerId;
    }

    public Optional<String> getCustomerId() {
        return Optional.ofNullable(customerId);
    }
}
