package com.microecom.customerservice.model.storage;

import com.microecom.customerservice.model.data.Customer;
import com.microecom.customerservice.model.data.ExistingCustomer;
import com.microecom.customerservice.model.storage.customer.CustomerCrudRepository;
import com.microecom.customerservice.model.storage.customer.data.CustomerRow;
import com.microecom.customerservice.model.storage.customer.data.Existing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class JpaCustomerRepository implements CustomerRepository {
    private final CustomerCrudRepository repo;

    public JpaCustomerRepository(@Autowired CustomerCrudRepository repo) {
        this.repo = repo;
    }

    private ExistingCustomer convertToExisting(CustomerRow row) {
        return new Existing(
                row.getId().toString(),
                row.getUserId(),
                row.getFirstName(),
                row.getLastName(),
                row.getEmail(),
                row.getSince()
        );
    }

    @Override
    public ExistingCustomer save(Customer customer) {
        var saved = repo.save(
                new CustomerRow(
                        customer.getUserId(),
                        customer.getEmail(),
                        customer.getFirstName(),
                        customer.getLastName()
                )
        );

        return convertToExisting(saved);
    }

    @Override
    public Optional<ExistingCustomer> findByUserId(String id) {
        return repo.findByUserId(id).map(this::convertToExisting);
    }
}
