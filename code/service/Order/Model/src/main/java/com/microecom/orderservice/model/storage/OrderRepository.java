package com.microecom.orderservice.model.storage;

import com.microecom.orderservice.model.data.ExistingOrder;
import com.microecom.orderservice.model.data.OrderUpdate;
import com.microecom.orderservice.model.data.OrdersCriteria;
import com.microecom.orderservice.model.exception.InvalidOrderDataException;
import com.microecom.orderservice.model.exception.OrderNotFoundException;
import com.microecom.orderservice.model.storage.data.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    ExistingOrder create(Order order) throws IllegalArgumentException;

    Optional<ExistingOrder> findById(String id);

    List<ExistingOrder> findList(OrdersCriteria criteria);

    void lockOrderForUpdate(String id);

    ExistingOrder update(OrderUpdate update) throws InvalidOrderDataException, OrderNotFoundException;
}
