package com.microecom.catalogservice.model.event;

import com.microecom.catalogservice.model.exception.ProductNotFoundException;
import com.microecom.inventoryservice.eventlist.StockChangedEvent;

/**
 * Processes Inventory service events.
 */
public interface InventoryListener {
    void consumeStockChanged(StockChangedEvent event) throws ProductNotFoundException;
}
