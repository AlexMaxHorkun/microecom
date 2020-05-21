package com.microecom.inventoryservice.model.stock;

import com.microecom.inventoryservice.model.ReservationsManager;
import com.microecom.inventoryservice.model.StockCalculator;
import com.microecom.inventoryservice.model.StockManager;
import com.microecom.inventoryservice.model.data.CalculatedAvailable;
import com.microecom.inventoryservice.model.data.Stock;
import com.microecom.inventoryservice.model.exception.NoStockFoundException;
import com.microecom.inventoryservice.model.stock.data.Calculated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class Calculator implements StockCalculator {
    private final StockManager manager;

    private final ReservationsManager reservations;

    public Calculator(@Autowired StockManager manager, @Autowired ReservationsManager reservations) {
        this.manager = manager;
        this.reservations = reservations;
    }

    @Override
    public Map<String, CalculatedAvailable> calculateAvailableFor(Set<String> productIds) throws NoStockFoundException {
        var stocks = manager.findByProductIds(productIds);
        var calculated = new HashMap<String, CalculatedAvailable>();
        for (Stock s : stocks) {
            var reserved = reservations.calculateReservedFor(s.getProductId());
            calculated.put(s.getProductId(), new Calculated(s.getProductId(), reserved.isPresent() ? s.getAvailable() - reserved.get() : s.getAvailable()));
        }

        return calculated;
    }
}
