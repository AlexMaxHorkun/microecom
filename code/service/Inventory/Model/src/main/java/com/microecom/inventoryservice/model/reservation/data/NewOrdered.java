package com.microecom.inventoryservice.model.reservation.data;

import com.microecom.inventoryservice.model.data.reservation.ReservedProduct;

public class NewOrdered implements ReservedProduct {
    private final String productId;

    private final Integer number;

    public NewOrdered(String productId, Integer number) {
        this.productId = productId;
        this.number = number;
    }

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public Integer getNumber() {
        return number;
    }
}
