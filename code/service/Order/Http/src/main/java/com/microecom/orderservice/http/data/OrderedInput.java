package com.microecom.orderservice.http.data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Ordered product info.
 */
public class OrderedInput {
    private String productId;

    private Integer quantity;

    public OrderedInput(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderedInput() {}

    public @NotEmpty String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public @NotNull @Min(1) @Max(100) Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
