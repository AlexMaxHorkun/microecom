package com.microecom.orderservice.model.service;

import com.microecom.orderservice.eventlist.OrderStatusChanged;
import com.microecom.orderservice.eventlist.data.OrderedProduct;
import com.microecom.orderservice.model.CatalogServiceClient;
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
import java.util.stream.Collectors;

@Service
public class OrderManagerService implements OrderManager {
    private final static Map<OrderStatus, com.microecom.orderservice.eventlist.data.OrderStatus> statuses = Map.of(OrderStatus.NEW, com.microecom.orderservice.eventlist.data.OrderStatus.NEW, OrderStatus.PAYMENT_FAILED, com.microecom.orderservice.eventlist.data.OrderStatus.PAYMENT_FAILED, OrderStatus.PROCESSED, com.microecom.orderservice.eventlist.data.OrderStatus.PROCESSED, OrderStatus.CANCELED, com.microecom.orderservice.eventlist.data.OrderStatus.CANCELED);

    private final OrderRepository repo;

    private final EventPublisher events;

    private final InventoryServiceClient inventoryClient;

    private final PaymentServiceClient paymentClient;

    private final CatalogServiceClient catalogClient;

    public OrderManagerService(@Autowired OrderRepository repo, @Autowired EventPublisher events, @Autowired InventoryServiceClient inventoryClient, @Autowired PaymentServiceClient paymentClient, @Autowired CatalogServiceClient catalogClient) {
        this.repo = repo;
        this.events = events;
        this.inventoryClient = inventoryClient;
        this.paymentClient = paymentClient;
        this.catalogClient = catalogClient;
    }

    @Transactional
    @Override
    public ExistingOrder place(NewOrder order) throws OutOfStockException, InvalidPaymentDetailsException {
        var productIds = order.getOrdered().stream().map(OrderedQuantity::getProductId).collect(Collectors.toSet());
        var outOfStock = new HashSet<String>();
        var stocks = inventoryClient.loadStocks(productIds);
        for (OrderedQuantity q : order.getOrdered()) {
            if (!stocks.containsKey(q.getProductId()) || stocks.get(q.getProductId()).getAvailable() < q.getQuantity()) {
                outOfStock.add(q.getProductId());
            }
        }
        if (!outOfStock.isEmpty()) {
            throw new OutOfStockException(outOfStock);
        }

        var products = catalogClient.loadProducts(productIds);
        var cost = 0.0;
        for (OrderedQuantity q : order.getOrdered()) {
            if (!products.containsKey(q.getProductId())) {
                throw new OutOfStockException(Set.of(q.getProductId()));
            }
            cost += q.getQuantity() * products.get(q.getProductId()).getPrice();
        }

        var created = repo.create(new SimpleOrder(OrderStatus.NEW, order.getCustomerId(), order.getOrdered(), cost));
        paymentClient.post(new NewPayment(created.getId(), created.getCustomerId(), order.getPaymentDetails(), cost));
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
            paymentClient.post(new NewPayment(order.getId(), order.getCustomerId(), update.getPaymentDetails().get(), order.getCost()));
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
            case CANCELED:
                if (order.getStatus() == OrderStatus.PROCESSED) {
                    throw new InvalidOrderStatusException();
                }
                break;
        }
        var existing = repo.update(new OrderStatusUpdate(order.getId(), status));
        publishStatusUpdatedEvent(order.getId(), order.getOrdered(), existing.getStatus());

        return existing;
    }
}
