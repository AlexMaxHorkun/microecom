package com.microecom.orderservice.eventlist.data;

public class OrderedProduct {
    private String productId;

    private Integer quantity;

    public OrderedProduct(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderedProduct() {}

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
