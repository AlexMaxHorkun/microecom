package com.microecom.catalogservice.model.data;

import java.util.Optional;

/**
 * Update to a product.
 */
public interface ProductUpdate {
    String getForId();

    Optional<String> getTitle();

    Optional<String> getDescription();

    Optional<Float> getPrice();

    Optional<String> getCategoryId();
}
