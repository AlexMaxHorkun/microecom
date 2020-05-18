package com.microecom.catalogservice.model;

import com.microecom.catalogservice.model.data.CategoryInfo;
import com.microecom.catalogservice.model.data.CategoryUpdate;
import com.microecom.catalogservice.model.data.ExistingCategory;
import com.microecom.catalogservice.model.exception.CategoryNotFoundException;
import com.microecom.catalogservice.model.storage.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryManagerService implements CategoryManager {
    private final CategoryRepository repo;

    private final ProductManager products;

    public CategoryManagerService(@Autowired CategoryRepository repo, @Autowired ProductManager products) {
        this.repo = repo;
        this.products = products;
    }

    @Override
    public ExistingCategory create(CategoryInfo newCategory) {
        return repo.create(newCategory);
    }

    @Override
    public ExistingCategory update(CategoryUpdate update) throws IllegalArgumentException {
        return repo.update(update);
    }

    @Override
    public void delete(String id) throws CategoryNotFoundException {
        repo.delete(id);
    }

    @Override
    public List<ExistingCategory> findList(boolean onlyWithProducts) {
        products.updateMissingAvailability();

        return repo.findAll(onlyWithProducts);
    }
}
