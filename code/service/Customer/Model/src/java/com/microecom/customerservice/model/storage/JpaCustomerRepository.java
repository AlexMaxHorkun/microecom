package com.microecom.customerservice.model.storage;

import com.microecom.customerservice.model.data.Customer;
import com.microecom.customerservice.model.data.CustomerUpdate;
import com.microecom.customerservice.model.data.ExistingCustomer;
import com.microecom.customerservice.model.storage.customer.CustomerCrudRepository;
import com.microecom.customerservice.model.storage.customer.data.CustomerRow;
import com.microecom.customerservice.model.storage.customer.data.Existing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
                row.getSince(),
                row.getDefaultShippingId().toString(),
                row.getDefaultBillingId().toString()
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
    public Optional<ExistingCustomer> findByUserId(String userId) {
        return repo.findByUserId(userId).map(this::convertToExisting);
    }

    @Override
    public Optional<ExistingCustomer> findById(String id) {
        return repo.findById(UUID.fromString(id)).map(this::convertToExisting);
    }

    @Override
    public ExistingCustomer update(CustomerUpdate update) throws IllegalArgumentException {
        var customer = repo.findById(UUID.fromString(update.getForId())).orElseThrow(IllegalArgumentException::new);
        if (update.getEmail().isPresent()) {
            customer.setEmail(update.getEmail().get());
        }
        if (update.getFirstName().isPresent()) {
            customer.setFirstName(update.getFirstName().get());
        }
        if (update.getLastName().isPresent()) {
            customer.setLastName(update.getLastName().get());
        }
        if (update.getDefaultShippingAddress().isPresent()) {
            customer.setDefaultShippingId(UUID.fromString(update.getDefaultShippingAddress().get()));
        }
        if (update.getDefaultBillingAddress().isPresent()) {
            customer.setDefaultBillingId(UUID.fromString(update.getDefaultBillingAddress().get()));
        }

        return convertToExisting(repo.save(customer));
    }

    @Override
    public void delete(String id) throws IllegalArgumentException {
        try {
            repo.deleteById(UUID.fromString(id));
        } catch (EmptyResultDataAccessException exception) {
            throw new IllegalArgumentException("Customer not found");
        }
    }
}
