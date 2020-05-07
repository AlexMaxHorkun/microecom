package com.microecom.catalogservice.model.storage.product;

import com.microecom.catalogservice.model.storage.product.data.ProductRow;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Set;
import java.util.UUID;

public interface ProductCrudRepository extends CrudRepository<ProductRow, UUID> {
    Iterable<ProductRow> findAllByCategory_Id(UUID id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p.id from ProductRow p where (:avail is null and p.available is null) or p.available = :avail")
    Iterable<UUID> findAllIdsByAvailable(@Param("avail") Boolean available);

    @Modifying
    @Query("update ProductRow p set p.available = ?2 where p.id in ?1")
    void updateAvailableFor(Set<UUID> ids, boolean available);
}
