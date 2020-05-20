package com.microecom.catalogservice.model.data;

import java.util.Optional;
import java.util.Set;

/**
 * Criteria for a list of products to find.
 */
public class ProductListCriteria {
    private final String categoryId;

    private final Boolean availableIsNull;

    private final Boolean available;

    private final Set<String> ids;

    public ProductListCriteria(String categoryId) {
        this.categoryId = categoryId;
        this.availableIsNull = null;
        this.available = true;
        this.ids = null;
    }

    public ProductListCriteria(String categoryId, Boolean availableIsNull) {
        this.categoryId = categoryId;
        this.availableIsNull = availableIsNull;
        this.available = true;
        this.ids = null;
    }

    public ProductListCriteria(String categoryId, Boolean availableIsNull, Boolean available) {
        this.categoryId = categoryId;
        this.availableIsNull = availableIsNull;
        this.available = available;
        this.ids = null;
    }

    public ProductListCriteria(String categoryId, Boolean availableIsNull, Boolean available, Set<String> ids) {
        this.categoryId = categoryId;
        this.availableIsNull = availableIsNull;
        this.available = available;
        this.ids = ids;
    }

    public Optional<String> getCategoryId() {
        return Optional.ofNullable(categoryId);
    }

    public Optional<Boolean> getAvailableIsNull() {
        return Optional.ofNullable(availableIsNull);
    }

    public Optional<Boolean> getAvailable() {
        return Optional.ofNullable(available);
    }

    public Optional<Set<String>> getIds() {
        return Optional.ofNullable(this.ids);
    }
}
