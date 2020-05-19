package com.microecom.orderservice.model.storage.order.data;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderRow {
    @Id
    private UUID id;

    private UUID customerId;

    private Integer status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProductRow> products;

    public OrderRow(UUID customerId, Integer status) {
        this.customerId = customerId;
        this.status = status;
        this.id = UUID.randomUUID();
    }

    public OrderRow() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<OrderProductRow> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderProductRow> products) {
        this.products = products;
    }
}
