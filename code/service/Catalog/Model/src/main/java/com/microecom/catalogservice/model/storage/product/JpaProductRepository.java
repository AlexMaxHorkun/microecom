package com.microecom.catalogservice.model.storage.product;

import com.microecom.catalogservice.model.data.ExistingProduct;
import com.microecom.catalogservice.model.data.ProductInfo;
import com.microecom.catalogservice.model.data.ProductListCriteria;
import com.microecom.catalogservice.model.data.ProductUpdate;
import com.microecom.catalogservice.model.storage.ProductRepository;
import com.microecom.catalogservice.model.storage.category.CategoryCrudRepository;
import com.microecom.catalogservice.model.storage.product.data.Existing;
import com.microecom.catalogservice.model.storage.product.data.ProductRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class JpaProductRepository implements ProductRepository {
    private final ProductCrudRepository repo;

    private final CategoryCrudRepository categoryRepo;

    public JpaProductRepository(@Autowired ProductCrudRepository repo, @Autowired CategoryCrudRepository categoryRepo) {
        this.repo = repo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public ExistingProduct create(ProductInfo newProduct) throws IllegalArgumentException {
        var categoryFound = categoryRepo.findById(UUID.fromString(newProduct.getCategoryId()));
        if (categoryFound.isEmpty()) {
            throw new IllegalArgumentException("Invalid category link");
        }

        return convertToExisting(
                new ProductRow(
                        newProduct.getSku(),
                        newProduct.getTitle(),
                        newProduct.getDescription(),
                        newProduct.getPrice(),
                        categoryFound.get()
                )
        );
    }

    @Override
    public ExistingProduct update(ProductUpdate update) throws IllegalArgumentException {
        var found = repo.findById(UUID.fromString(update.getForId()));
        if (found.isEmpty()) {
            throw new IllegalArgumentException("Product with given Id not found");
        }

        var row = found.get();
        if (update.getCategoryId().isPresent()) {
            var foundCategory = categoryRepo.findById(UUID.fromString(update.getCategoryId().get()));
            if (foundCategory.isEmpty()) {
                throw new IllegalArgumentException("Invalid category link");
            }
            row.setCategory(foundCategory.get());
        }
        if (update.getTitle().isPresent()) {
            row.setTitle(update.getTitle().get());
        }
        if (update.getDescription().isPresent()) {
            row.setDescription(update.getDescription().get());
        }
        if (update.getPrice().isPresent()) {
            row.setPrice(update.getPrice().get());
        }

        return convertToExisting(repo.save(row));
    }

    @Override
    public void delete(String id) throws IllegalArgumentException {
        try {
            repo.deleteById(UUID.fromString(id));
        } catch (EmptyResultDataAccessException exception) {
            throw new IllegalArgumentException("Product not found");
        }
    }

    @Override
    public List<ExistingProduct> findAll(ProductListCriteria criteria) {
        Iterable<ProductRow> found;
        if (criteria.getCategoryId().isEmpty()) {
            found = repo.findAll();
        } else {
            found = repo.findAllByCategory_Id(UUID.fromString(criteria.getCategoryId().get()));
        }

        var list = new ArrayList<ExistingProduct>();
        for (ProductRow row : found) {
            list.add(convertToExisting(row));
        }

        return list;
    }

    private Existing convertToExisting(ProductRow row) {
        Boolean available = null;
        if (row.getAvailable().isPresent()) {
            available = row.getAvailable().get();
        }

        return new Existing(
                row.getId().toString(),
                available,
                row.getSku(),
                row.getTitle(),
                row.getDescription(),
                row.getCategoryId().toString(),
                row.getPrice()
        );
    }
}
