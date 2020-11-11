package com.microecom.customerservice.model.storage;

import com.microecom.customerservice.model.data.Customer;
import com.microecom.customerservice.model.data.CustomerUpdate;
import com.microecom.customerservice.model.data.ExistingCustomer;
import com.microecom.customerservice.model.exception.InvalidCustomerDataException;
import com.microecom.customerservice.model.exception.NotFoundException;
import com.microecom.customerservice.model.storage.data.CustomerToProcess;
import com.microecom.customerservice.model.storage.data.UserDataUpdate;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Repo for customer objects.
 */
public interface CustomerRepository {
    ExistingCustomer save(Customer customer) throws InvalidCustomerDataException;

    Optional<ExistingCustomer> findByUserId(String userId);

    Optional<ExistingCustomer> findById(String id);

    ExistingCustomer update(CustomerUpdate update) throws InvalidCustomerDataException, NotFoundException;

    void delete(String id) throws NotFoundException;

    long count();

    Stream<CustomerToProcess> readBatch(int size, int no);

    void updateWithUserData(UserDataUpdate[] updates);
}
