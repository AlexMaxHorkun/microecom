package com.microecom.orderservice.model;

import com.microecom.orderservice.model.data.ExistingOrder;
import com.microecom.orderservice.model.data.NewOrder;
import com.microecom.orderservice.model.data.OrderUpdate;
import com.microecom.orderservice.model.exception.InvalidPaymentDetailsException;
import com.microecom.orderservice.model.exception.OrderNotFoundException;
import com.microecom.orderservice.model.exception.OutOfStockException;

import java.util.List;
import java.util.Optional;

/**
 * Manages orders.
 */
public interface OrderManager {
    ExistingOrder place(NewOrder order) throws OutOfStockException, InvalidPaymentDetailsException;

    Optional<ExistingOrder> findById(String id);

    List<ExistingOrder> findList();

    ExistingOrder update(OrderUpdate update) throws OrderNotFoundException;
}
