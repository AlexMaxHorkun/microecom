package com.microecom.catalogservice.model.storage.product;

import com.microecom.catalogservice.model.storage.product.data.ProductRow;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductCrudRepository extends CrudRepository<ProductRow, UUID> {
    Iterable<ProductRow> findAllByCategory_Id(UUID id);
}
