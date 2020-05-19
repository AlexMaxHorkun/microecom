package com.microecom.orderservice.model;

import com.microecom.orderservice.model.data.ProductStock;

import java.util.Map;
import java.util.Set;

/**
 * Client for Inventory service.
 */
public interface InventoryServiceClient {
    Map<String, ProductStock> loadStocks(Set<String> productIds);
}
