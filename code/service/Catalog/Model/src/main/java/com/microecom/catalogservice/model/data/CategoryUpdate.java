package com.microecom.catalogservice.model.data;

import java.util.Optional;

/**
 * Update to a category.
 */
public interface CategoryUpdate {
    String getForId();

    Optional<String> getName();
}
