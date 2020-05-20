package com.microecom.orderservice.model.service;

import com.microecom.orderservice.eventlist.OrderStatusChanged;
import com.microecom.orderservice.eventlist.data.OrderedProduct;
import com.microecom.orderservice.model.InventoryServiceClient;
import com.microecom.orderservice.model.OrderManager;
import com.microecom.orderservice.model.PaymentServiceClient;
import com.microecom.orderservice.model.data.*;
import com.microecom.orderservice.model.event.EventPublisher;
import com.microecom.orderservice.model.event.data.Event;
import com.microecom.orderservice.model.exception.*;
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
        var outOfStock = new HashSet<String>();
        for (OrderedQuantity q : order.getOrdered()) {
            outOfStock.add(q.getProductId());
        }
        var stocks = inventoryClient.loadStocks(outOfStock);
        for (OrderedQuantity q : order.getOrdered()) {
            if (stocks.containsKey(q.getProductId()) && stocks.get(q.getProductId()).getAvailable() >= q.getQuantity()) {
                outOfStock.remove(q.getProductId());
            }
        }
        if (!outOfStock.isEmpty()) {
            throw new OutOfStockException(outOfStock);
        }

        var created = repo.create(new SimpleOrder(OrderStatus.NEW, order.getCustomerId(), order.getOrdered()));
        paymentClient.post(new NewPayment(created.getId(), created.getCustomerId(), order.getPaymentDetails()));
        publishStatusUpdatedEvent(created.getId(), created.getOrdered(), created.getStatus());

        return created;
    }

    @Override
    public Optional<ExistingOrder> findById(String id) {
        return repo.findById(id);
    }

    @Override
    public List<ExistingOrder> findList(OrdersCriteria criteria) {
        return repo.findList(criteria);
    }

    @Transactional
    @Override
    public ExistingOrder update(OrderUpdate update) throws OrderNotFoundException, InvalidOrderDataException, InvalidPaymentDetailsException {
        var found = findById(update.getForOrderId());
        if (found.isEmpty()) {
            throw new OrderNotFoundException();
        }
        if (update.getStatus().isEmpty() && update.getPaymentDetails().isEmpty()) {
            throw new InvalidOrderDataException();
        }

        var order = found.get();
        if (update.getStatus().isPresent()) {
            order = updateStatus(found.get(), update.getStatus().get());
        }
        if (update.getPaymentDetails().isPresent()) {
            paymentClient.post(new NewPayment(order.getId(), order.getCustomerId(), update.getPaymentDetails().get()));
        }

        return order;
    }

    private void publishStatusUpdatedEvent(String orderId, Set<OrderedQuantity> ordered, OrderStatus status) {
        var converted = new HashSet<OrderedProduct>();
        for (OrderedQuantity q : ordered) {
            converted.add(new OrderedProduct(q.getProductId(), q.getQuantity()));
        }

        events.publish(
                new Event(
                        "order-status-changed",
                        new OrderStatusChanged(
                                orderId,
                                converted,
                                statuses.get(status)
                        )
                )
        );
    }

    private ExistingOrder updateStatus(ExistingOrder order, OrderStatus status) throws InvalidOrderStatusException {
        repo.lockOrderForUpdate(order.getId());
        if (order.getStatus() == status) {
            throw new InvalidOrderStatusException();
        }
        switch (status) {
            case NEW:
                throw new InvalidOrderStatusException();
            case PROCESSED:
                if (order.getStatus() == OrderStatus.CANCELED) {
                    throw new InvalidOrderStatusException();
                }
                break;
            case PAYMENT_FAILED:
                if (order.getStatus() == OrderStatus.CANCELED || order.getStatus() == OrderStatus.PROCESSED) {
                    throw new InvalidOrderStatusException();
                }
                break;
        }
        var existing = repo.update(new OrderStatusUpdate(order.getId(), status));
        publishStatusUpdatedEvent(order.getId(), order.getOrdered(), existing.getStatus());

        return existing;
    }
}
