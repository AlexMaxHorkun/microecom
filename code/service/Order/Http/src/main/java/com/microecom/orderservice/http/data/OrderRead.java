package com.microecom.orderservice.http.data;

import com.microecom.orderservice.model.data.ExistingOrder;
import com.microecom.orderservice.model.data.OrderStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class OrderRead {
    private Set<String> productIds;

    private String id;

    private OrderStatus status;

    public static OrderRead of(ExistingOrder order) {
        return new OrderRead(order.getProductIds(), order.getId(), order.getStatus());
    }

    public static List<OrderRead> of(Collection<ExistingOrder> orders) {
        var list = new ArrayList<OrderRead>();
        for (ExistingOrder o : orders) {
            list.add(OrderRead.of(o));
        }

        return list;
    }

    public OrderRead() {}

    public OrderRead(Set<String> productIds, String id, OrderStatus status) {
        this.productIds = productIds;
        this.id = id;
        this.status = status;
    }

    public Set<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<String> productIds) {
        this.productIds = productIds;
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
}
