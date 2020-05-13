package com.microecom.catalogservice.model.storage;

import com.microecom.catalogservice.model.data.ExistingProduct;
import com.microecom.catalogservice.model.data.ProductInfo;
import com.microecom.catalogservice.model.data.ProductListCriteria;
import com.microecom.catalogservice.model.data.ProductUpdate;
import com.microecom.catalogservice.model.storage.data.ProductAvailabilityUpdate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository {
    ExistingProduct create(ProductInfo newProduct) throws IllegalArgumentException;

    ExistingProduct update(ProductUpdate update) throws IllegalArgumentException;

    void delete(String id) throws IllegalArgumentException;

    List<ExistingProduct> findAll(ProductListCriteria criteria);

    Iterable<String> findAllIdsForUpdate(ProductListCriteria criteria);

    void updateAvailability(Iterable<ProductAvailabilityUpdate> forProducts);

    Optional<ExistingProduct> findById(String id);
}
