package com.microecom.catalogservice.model;

import com.microecom.catalogservice.model.data.*;
import com.microecom.inventoryservice.eventlist.StockChangedEvent;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Manages products.
 */
public interface ProductManager {
    ExistingProduct create(ProductInfo newProduct) throws IllegalArgumentException;

    ExistingProduct update(ProductUpdate update) throws IllegalArgumentException;

    void delete(String id) throws IllegalArgumentException;

    List<ExistingProduct> findList(ProductListCriteria criteria) throws IllegalArgumentException;

    Set<String> updateMissingAvailability();

    Optional<ExistingProduct> findById(String id);

    void consumeStockUpdate(StockChangedEvent update) throws IllegalArgumentException;
}
