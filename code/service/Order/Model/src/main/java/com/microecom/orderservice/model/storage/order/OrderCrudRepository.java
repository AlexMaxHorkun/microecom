package com.microecom.orderservice.model.storage.order;

import com.microecom.orderservice.model.storage.order.data.OrderRow;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderCrudRepository extends CrudRepository<OrderRow, UUID> {
}
