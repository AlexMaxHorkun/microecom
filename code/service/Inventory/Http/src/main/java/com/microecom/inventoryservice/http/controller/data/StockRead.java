package com.microecom.inventoryservice.http.controller.data;

import com.microecom.inventoryservice.model.data.CalculatedAvailable;
import com.microecom.inventoryservice.model.data.Stock;

/**
 * Stock output.
 */
public class StockRead {
    private String productId;

    private Integer available;

    public static StockRead of(Stock stock) {
        return new StockRead(stock.getProductId(), stock.getAvailable());
    }

    public static StockRead of(CalculatedAvailable calculated) {
        return new StockRead(calculated.getForProductId(), calculated.getCalculated());
    }

    public StockRead(String productId, Integer available) {
        this.productId = productId;
        this.available = available;
    }

    public StockRead() {}

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
