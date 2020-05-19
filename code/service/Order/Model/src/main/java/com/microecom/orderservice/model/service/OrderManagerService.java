package com.microecom.orderservice.model.service;

import com.microecom.orderservice.eventlist.OrderStatusChanged;
import com.microecom.orderservice.model.InventoryServiceClient;
import com.microecom.orderservice.model.OrderManager;
import com.microecom.orderservice.model.PaymentServiceClient;
import com.microecom.orderservice.model.data.*;
import com.microecom.orderservice.model.event.EventPublisher;
import com.microecom.orderservice.model.event.data.Event;
import com.microecom.orderservice.model.exception.InvalidPaymentDetailsException;
import com.microecom.orderservice.model.exception.OrderNotFoundException;
import com.microecom.orderservice.model.exception.OutOfStockException;
import com.microecom.orderservice.model.storage.OrderRepository;
import com.microecom.orderservice.model.storage.data.SimpleOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderManagerService implements OrderManager {
    private final static Map<OrderStatus, com.microecom.orderservice.eventlist.data.OrderStatus> statuses = Map.of(OrderStatus.NEW, com.microecom.orderservice.eventlist.data.OrderStatus.NEW, OrderStatus.PAYMENT_FAILED, com.microecom.orderservice.eventlist.data.OrderStatus.PAYMENT_FAILED, OrderStatus.PROCESSED, com.microecom.orderservice.eventlist.data.OrderStatus.PROCESSED, OrderStatus.CANCELED, com.microecom.orderservice.eventlist.data.OrderStatus.CANCELED);

    private final OrderRepository repo;

    private final EventPublisher events;

    private final InventoryServiceClient inventoryClient;

    private final PaymentServiceClient paymentClient;

    public OrderManagerService(@Autowired OrderRepository repo, @Autowired EventPublisher events, @Autowired InventoryServiceClient inventoryClient, @Autowired PaymentServiceClient paymentClient) {
        this.repo = repo;
        this.events = events;
        this.inventoryClient = inventoryClient;
        this.paymentClient = paymentClient;
    }

    @Transactional
    @Override
    public ExistingOrder place(NewOrder order) throws OutOfStockException, InvalidPaymentDetailsException {
        var stocks = inventoryClient.loadStocks(order.getProductIds());
        var outOfStock = new HashSet<String>();
        for (String p : order.getProductIds()) {
            if (!stocks.containsKey(p) || stocks.get(p).getAvailable() < 1) {
                outOfStock.add(p);
            }
        }
        if (!outOfStock.isEmpty()) {
            throw new OutOfStockException(outOfStock);
        }

        var created = repo.create(new SimpleOrder(OrderStatus.NEW, order.getCustomerId(), order.getProductIds()));
        paymentClient.post(new NewPayment(created.getId(), created.getCustomerId(), order.getPaymentDetails()));
        events.publish(
                new Event(
                        "order-status-changed",
                        new OrderStatusChanged(
                                created.getId(),
                                created.getProductIds(),
                                statuses.get(created.getStatus())
                        )
                )
        );

        return created;
    }

    @Override
    public Optional<ExistingOrder> findById(String id) {
        return repo.findById(id);
    }

    @Override
    public List<ExistingOrder> findList() {
        return repo.findList();
    }

    @Override
    public ExistingOrder update(OrderUpdate update) throws OrderNotFoundException {
        return null;
    }
}
