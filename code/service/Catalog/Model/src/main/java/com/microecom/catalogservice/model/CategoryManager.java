package com.microecom.catalogservice.model;

import com.microecom.catalogservice.model.data.CategoryInfo;
import com.microecom.catalogservice.model.data.CategoryUpdate;
import com.microecom.catalogservice.model.data.ExistingCategory;
import com.microecom.catalogservice.model.exception.CategoryNotFoundException;

import java.util.List;

/**
 * Manages categories.
 */
public interface CategoryManager {
    ExistingCategory create(CategoryInfo newCategory);

    ExistingCategory update(CategoryUpdate update) throws IllegalArgumentException;

    void delete(String id) throws CategoryNotFoundException;

    List<ExistingCategory> findList(boolean onlyWithProducts);
}
