package com.microecom.inventoryservice.model.reservation.data;

import com.microecom.inventoryservice.model.data.StockUpdate;

import java.util.Optional;

public class StockSubUpdate implements StockUpdate {
    private final String productId;

    private final Integer sub;

    public StockSubUpdate(String productId, Integer sub) {
        this.productId = productId;
        if (sub <= 0) {
            throw new IllegalArgumentException("Substructed number of items must be gt 0");
        }
        this.sub = sub;
    }

    @Override
    public String getForProductId() {
        return productId;
    }

    @Override
    public Optional<Integer> getAvailable() {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> getSubAvailable() {
        return Optional.of(sub);
    }
}
