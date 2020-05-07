package com.microecom.catalogservice.model;

import com.microecom.catalogservice.model.data.ExistingProduct;
import com.microecom.catalogservice.model.data.ProductInfo;
import com.microecom.catalogservice.model.data.ProductListCriteria;
import com.microecom.catalogservice.model.data.ProductUpdate;

import java.util.List;
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
}
