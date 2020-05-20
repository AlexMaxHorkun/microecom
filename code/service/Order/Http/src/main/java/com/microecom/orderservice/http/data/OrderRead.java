package com.microecom.orderservice.http.data;

import com.microecom.orderservice.model.data.ExistingOrder;
import com.microecom.orderservice.model.data.OrderStatus;
import com.microecom.orderservice.model.data.OrderedQuantity;

import java.util.*;

public class OrderRead {
    private Set<OrderedRead> ordered;

    private String id;

    private OrderStatus status;

    public static OrderRead of(ExistingOrder order) {
        var ordered = new HashSet<OrderedRead>();
        for (OrderedQuantity q : order.getOrdered()) {
            ordered.add(OrderedRead.of(q));
        }

        return new OrderRead(ordered, order.getId(), order.getStatus());
    }

    public static List<OrderRead> of(Collection<ExistingOrder> orders) {
        var list = new ArrayList<OrderRead>();
        for (ExistingOrder o : orders) {
            list.add(OrderRead.of(o));
        }

        return list;
    }

    public OrderRead() {}

    public OrderRead(Set<OrderedRead> ordered, String id, OrderStatus status) {
        this.ordered = ordered;
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<OrderedRead> getOrdered() {
        return ordered;
    }

    public void setOrdered(Set<OrderedRead> ordered) {
        this.ordered = ordered;
    }
}
