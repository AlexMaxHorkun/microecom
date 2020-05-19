package com.microecom.orderservice.model.storage.data;

import com.microecom.orderservice.model.data.OrderStatus;

import java.util.Set;

public interface Order {
    OrderStatus getStatus();

    String getCustomerId();

    Set<String> getProductIds();
}
