package com.microecom.inventoryservice.model.storage.stock;

import com.microecom.inventoryservice.model.storage.stock.data.StockRow;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

public interface StockCrudRepository extends CrudRepository<StockRow, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from StockRow s where s.productId = ?1")
    Optional<StockRow> findLockedById(UUID id);
}
