package com.microecom.catalogservice.model.storage.category;

import com.microecom.catalogservice.model.data.CategoryInfo;
import com.microecom.catalogservice.model.data.CategoryUpdate;
import com.microecom.catalogservice.model.data.ExistingCategory;
import com.microecom.catalogservice.model.storage.CategoryRepository;
import com.microecom.catalogservice.model.storage.category.data.CategoryRow;
import com.microecom.catalogservice.model.storage.category.data.Existing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaCategoryRepository implements CategoryRepository {
    private final CategoryCrudRepository repo;

    public JpaCategoryRepository(@Autowired CategoryCrudRepository repo) {
        this.repo = repo;
    }

    @Override
    public ExistingCategory create(CategoryInfo newCategory) {
        return convertToExisting(repo.save(new CategoryRow(newCategory.getName())));
    }

    @Override
    public ExistingCategory update(CategoryUpdate update) throws IllegalArgumentException {
        var found = repo.findById(UUID.fromString(update.getForId()));
        if (found.isEmpty()) {
            throw new IllegalArgumentException("Category not found by given ID");
        }

        var row = found.get();
        if (update.getName().isPresent()) {
            row.setName(update.getName().get());
        }

        return convertToExisting(repo.save(row));
    }

    @Override
    public void delete(String id) throws IllegalArgumentException {
        try {
            repo.deleteById(UUID.fromString(id));
        } catch (EmptyResultDataAccessException exception) {
            throw new IllegalArgumentException("Category not found");
        }
    }

    @Override
    public Optional<ExistingCategory> findById(String id) {
        return repo.findById(UUID.fromString(id)).map(this::convertToExisting);
    }

    @Override
    public List<ExistingCategory> findAll(boolean onlyWithProducts) {
        Iterable<CategoryRow> found;
        if (onlyWithProducts) {
            found = repo.findWithProducts();
        } else {
            found = repo.findAll();
        }
        var list = new ArrayList<ExistingCategory>();
        for (CategoryRow row : found) {
            list.add(convertToExisting(row));
        }

        return list;
    }

    private ExistingCategory convertToExisting(CategoryRow row) {
        return new Existing(row.getId().toString(), row.getName());
    }
}
