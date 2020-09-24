package com.microecom.customerservice.model.storage.customer;

import com.microecom.customerservice.model.storage.customer.data.CustomerRow;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerCrudRepository extends CrudRepository<CustomerRow, UUID> {
    Optional<CustomerRow> findByUserId(String userId);
}
