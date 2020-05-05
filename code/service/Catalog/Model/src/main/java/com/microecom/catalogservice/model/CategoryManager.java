package com.microecom.catalogservice.model;

import com.microecom.catalogservice.model.data.CategoryInfo;
import com.microecom.catalogservice.model.data.CategoryUpdate;
import com.microecom.catalogservice.model.data.ExistingCategory;

import java.util.List;

/**
 * Manages categories.
 */
public interface CategoryManager {
    ExistingCategory create(CategoryInfo newCategory);

    ExistingCategory update(CategoryUpdate update) throws IllegalArgumentException;

    void delete(String id) throws IllegalArgumentException;

    List<ExistingCategory> findList(boolean onlyWithProducts);
}
