package com.microecom.inventoryservice.model.stock.data;

import com.microecom.inventoryservice.model.data.CalculatedAvailable;

public class Calculated implements CalculatedAvailable {
    private String forProductId;

    private Integer calculated;

    public Calculated(String forProductId, Integer calculated) {
        this.forProductId = forProductId;
        this.calculated = calculated;
    }

    public Calculated() {}

    @Override
    public String getForProductId() {
        return forProductId;
    }

    @Override
    public Integer getCalculated() {
        return calculated;
    }

    public void setForProductId(String forProductId) {
        this.forProductId = forProductId;
    }

    public void setCalculated(Integer calculated) {
        this.calculated = calculated;
    }
}
