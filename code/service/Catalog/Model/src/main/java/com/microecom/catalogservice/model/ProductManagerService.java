package com.microecom.catalogservice.model;

import com.microecom.catalogservice.model.data.ExistingProduct;
import com.microecom.catalogservice.model.data.ProductInfo;
import com.microecom.catalogservice.model.data.ProductListCriteria;
import com.microecom.catalogservice.model.data.ProductUpdate;
import com.microecom.catalogservice.model.storage.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductManagerService implements ProductManager {
    private final ProductRepository repo;

    public ProductManagerService(@Autowired ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public ExistingProduct create(ProductInfo newProduct) throws IllegalArgumentException {
        return repo.create(newProduct);
    }

    @Override
    public ExistingProduct update(ProductUpdate update) throws IllegalArgumentException {
        return repo.update(update);
    }

    @Override
    public void delete(String id) throws IllegalArgumentException {
        repo.delete(id);
    }

    @Override
    public List<ExistingProduct> findList(ProductListCriteria criteria) throws IllegalArgumentException {
        return repo.findAll(criteria);
    }
}
