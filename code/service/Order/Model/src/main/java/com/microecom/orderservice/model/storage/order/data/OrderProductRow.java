package com.microecom.orderservice.model.storage.order.data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ordered_products")
public class OrderProductRow {
    @ManyToOne(cascade = {})
    @JoinColumn(name = "order_id")
    private OrderRow order;

    @Id
    private UUID orderId;

    private UUID productId;

    private Integer qty;

    public OrderProductRow(OrderRow order, UUID productId, Integer qty) {
        this.order = order;
        this.productId = productId;
        this.qty = qty;
    }

    public OrderProductRow() {}

    public OrderRow getOrder() {
        return order;
    }

    public void setOrder(OrderRow order) {
        this.order = order;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
