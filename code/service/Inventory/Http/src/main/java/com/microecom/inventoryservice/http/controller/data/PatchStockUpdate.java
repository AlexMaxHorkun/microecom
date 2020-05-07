package com.microecom.inventoryservice.http.controller.data;

import com.microecom.inventoryservice.model.data.StockUpdate;

import javax.validation.constraints.Min;
import java.util.Optional;

/**
 * Stock update input.
 */
public class PatchStockUpdate implements StockUpdate {
    private String forProductId;

    private Integer available;

    public PatchStockUpdate(String forProductId, Integer available) {
        this.forProductId = forProductId;
        this.available = available;
    }

    public PatchStockUpdate() {}

    @Override
    public String getForProductId() {
        return forProductId;
    }

    @Override
    public Optional<@Min(0) Integer> getAvailable() {
        return Optional.ofNullable(available);
    }

    public void setForProductId(String forProductId) {
        this.forProductId = forProductId;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
