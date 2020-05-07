package com.microecom.inventoryservice.model.storage.stock.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "stocks")
public class StockRow {
    @Id
    private UUID productId;

    private Integer available;

    public StockRow(UUID productId, Integer available) {
        this.productId = productId;
        this.available = available;
    }

    public StockRow() {}

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
