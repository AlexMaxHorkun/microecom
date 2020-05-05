package com.microecom.catalogservice.model.storage.category;

import com.microecom.catalogservice.model.storage.category.data.CategoryRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryCrudRepository extends CrudRepository<CategoryRow, UUID> {
    @Query("select c from CategoryRow c join ProductRow p on p.category = c where p is not null group by c")
    List<CategoryRow> findWithProducts();
}
