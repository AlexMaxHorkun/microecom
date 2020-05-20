package com.microecom.orderservice.model.storage.order;

import com.microecom.orderservice.model.storage.order.data.OrderRow;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.UUID;

public interface OrderCrudRepository extends CrudRepository<OrderRow, UUID> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "select o from OrderRow o where o.id = :id")
    OrderRow lockOrder(@Param("id") UUID id);

    Iterable<OrderRow> findByCustomerId(UUID id);
}
