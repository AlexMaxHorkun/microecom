package com.microecom.orderservice.model.storage.order;

import com.microecom.orderservice.model.data.ExistingOrder;
import com.microecom.orderservice.model.data.OrderStatus;
import com.microecom.orderservice.model.data.OrderUpdate;
import com.microecom.orderservice.model.data.OrdersCriteria;
import com.microecom.orderservice.model.exception.InvalidOrderDataException;
import com.microecom.orderservice.model.exception.OrderNotFoundException;
import com.microecom.orderservice.model.storage.OrderRepository;
import com.microecom.orderservice.model.storage.data.Order;
import com.microecom.orderservice.model.storage.order.data.Existing;
import com.microecom.orderservice.model.storage.order.data.OrderProductRow;
import com.microecom.orderservice.model.storage.order.data.OrderRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JpaOrderRepository implements OrderRepository {
    private final static Map<Integer, OrderStatus> statuses = Map.of(0, OrderStatus.NEW, 1, OrderStatus.PAYMENT_FAILED, 2, OrderStatus.PROCESSED, 3, OrderStatus.CANCELED);

    private final static Map<OrderStatus, Integer> statusesReversed = statuses.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    private final OrderCrudRepository repo;

    public JpaOrderRepository(@Autowired OrderCrudRepository repo) {
        this.repo = repo;
    }

    @Transactional
    @Override
    public ExistingOrder create(Order order) throws IllegalArgumentException {
        var orderRow = new OrderRow(UUID.fromString(order.getCustomerId()), statusesReversed.get(order.getStatus()));
        var products = new HashSet<OrderProductRow>();
        for (String productId : order.getProductIds()) {
            products.add(new OrderProductRow(orderRow, UUID.fromString(productId)));
        }
        orderRow.setProducts(products);

        return convert(repo.save(orderRow));
    }

    @Override
    public Optional<ExistingOrder> findById(String id) {
        return repo.findById(UUID.fromString(id)).map(this::convert);
    }

    @Override
    public List<ExistingOrder> findList(OrdersCriteria criteria) {
        var list = new ArrayList<ExistingOrder>();
        Iterable<OrderRow> found;
        if (criteria.getCustomerId().isPresent()) {
            found = repo.findByCustomerId(UUID.fromString(criteria.getCustomerId().get()));
        } else {
            found = repo.findAll();
        }
        for (OrderRow row : found) {
            list.add(convert(row));
        }

        return list;
    }

    private ExistingOrder convert(OrderRow row) {
        var products = new HashSet<String>();
        for (OrderProductRow p : row.getProducts()) {
            products.add(p.getProductId().toString());
        }

        return new Existing(row.getId().toString(), statuses.get(row.getStatus()), row.getCustomerId().toString(), products);
    }

    @Transactional
    @Override
    public void lockOrderForUpdate(String id) {
        repo.lockOrder(UUID.fromString(id));
    }

    @Transactional
    @Override
    public ExistingOrder update(OrderUpdate update) throws InvalidOrderDataException, OrderNotFoundException {
        var found = repo.findById(UUID.fromString(update.getForOrderId()));
        if (found.isEmpty()) {
            throw new OrderNotFoundException();
        }
        var row = found.get();
        if (update.getStatus().isPresent()) {
            row.setStatus(statusesReversed.get(update.getStatus().get()));
        }

        return convert(repo.save(row));
    }
}
