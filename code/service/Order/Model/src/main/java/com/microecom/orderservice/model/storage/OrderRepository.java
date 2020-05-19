package com.microecom.orderservice.model.storage;

import com.microecom.orderservice.model.data.ExistingOrder;
import com.microecom.orderservice.model.storage.data.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    ExistingOrder create(Order order) throws IllegalArgumentException;

    Optional<ExistingOrder> findById(String id);

    List<ExistingOrder> findList();
}
