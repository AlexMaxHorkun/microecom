package com.microecom.orderservice.http.data;

import com.microecom.orderservice.model.data.OrderedQuantity;

public class OrderedRead {
    private String productId;

    private Integer quantity;

    public static OrderedRead of(OrderedQuantity q) {
        return new OrderedRead(q.getProductId(), q.getQuantity());
    }

    public OrderedRead(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderedRead() {}

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
