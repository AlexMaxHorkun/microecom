package com.microecom.catalogservice.model.storage;

import com.microecom.catalogservice.model.data.CategoryInfo;
import com.microecom.catalogservice.model.data.CategoryUpdate;
import com.microecom.catalogservice.model.data.ExistingCategory;
import com.microecom.catalogservice.model.exception.CategoryNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    ExistingCategory create(CategoryInfo newCategory);

    ExistingCategory update(CategoryUpdate update) throws IllegalArgumentException;

    void delete(String id) throws CategoryNotFoundException;

    Optional<ExistingCategory> findById(String id);

    List<ExistingCategory> findAll(boolean onlyWithProducts);
}
