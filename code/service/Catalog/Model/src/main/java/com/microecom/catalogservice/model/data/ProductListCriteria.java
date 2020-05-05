package com.microecom.catalogservice.model.data;

import java.util.Optional;

/**
 * Criteria for a list of products to find.
 */
public class ProductListCriteria {
    private final String categoryId;

    public ProductListCriteria(String categoryId) {
        this.categoryId = categoryId;
    }

    public Optional<String> getCategoryId() {
        return Optional.ofNullable(categoryId);
    }
}
