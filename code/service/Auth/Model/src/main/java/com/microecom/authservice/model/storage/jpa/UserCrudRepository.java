package com.microecom.authservice.model.storage.jpa;

import com.microecom.authservice.model.storage.data.UserCustomerUpdate;
import com.microecom.authservice.model.storage.jpa.data.UserRow;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserCrudRepository extends CrudRepository<UserRow, UUID> {
    public List<UserRow> findAllByIdIn(Set<UUID> uuids);

    @Modifying
    @Query(value = "update UserRow u set u.customerId = :#{#update.customerId}, u.customerEmail = :#{#update.customerEmail}, u.customerFirstname = :#{#update.customerFirstname}, u.customerLastname = :#{#update.customerLastname} where u.id = :#{#update.userId}")
    public int addCustomerData(@Param("update") UserCustomerUpdate update);
}
