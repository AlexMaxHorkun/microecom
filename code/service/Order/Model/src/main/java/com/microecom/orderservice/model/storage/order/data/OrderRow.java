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

    private Double cost;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProductRow> products;

    public OrderRow(UUID customerId, Integer status, Double cost) {
        this.customerId = customerId;
        this.status = status;
        this.id = UUID.randomUUID();
        this.cost = cost;
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
