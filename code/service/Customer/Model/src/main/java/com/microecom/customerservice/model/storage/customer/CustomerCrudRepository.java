package com.microecom.customerservice.model.storage.customer;

import com.microecom.customerservice.model.storage.customer.data.CustomerRow;
import com.microecom.customerservice.model.storage.data.UserDataUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CustomerCrudRepository extends CrudRepository<CustomerRow, UUID> {
    Optional<CustomerRow> findByUserId(String userId);

    @Query(value = "select c from CustomerRow c order by c.id")
    Page<CustomerRow> findInBatches(Pageable pageable);

    @Modifying
    @Query(value = "update CustomerRow c set c.created = :#{#update.created} where c.id = :#{#update.customerId}")
    int updateCreated(@Param("update") UserDataUpdate update);
}
