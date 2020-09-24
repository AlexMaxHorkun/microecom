package com.microecom.customerservice.model.storage.address;

import com.microecom.customerservice.model.storage.address.data.AddressRow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AddressCrudRepository extends CrudRepository<AddressRow, UUID> {
    List<AddressRow> findAllByCustomer_Id(UUID id);
}
