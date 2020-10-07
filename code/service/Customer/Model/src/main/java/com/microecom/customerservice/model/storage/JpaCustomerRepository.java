package com.microecom.customerservice.model.storage;

import com.microecom.customerservice.model.data.Customer;
import com.microecom.customerservice.model.data.CustomerUpdate;
import com.microecom.customerservice.model.data.ExistingCustomer;
import com.microecom.customerservice.model.exception.InvalidCustomerDataException;
import com.microecom.customerservice.model.exception.NotFoundException;
import com.microecom.customerservice.model.storage.customer.CustomerCrudRepository;
import com.microecom.customerservice.model.storage.customer.data.CustomerRow;
import com.microecom.customerservice.model.storage.customer.data.Existing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        String shipId = null;
        if (row.getDefaultShippingId() != null) {
            shipId = row.getDefaultShippingId().toString();
        }
        String billId = null;
        if (row.getDefaultBillingId() != null) {
            billId = row.getDefaultBillingId().toString();
        }

        return new Existing(
                row.getId().toString(),
                row.getUserId(),
                row.getFirstName(),
                row.getLastName(),
                row.getEmail(),
                row.getSince(),
                shipId,
                billId
        );
    }

    @Override
    public ExistingCustomer save(Customer customer) {
        try {
            var saved = repo.save(
                    new CustomerRow(
                            customer.getUserId(),
                            customer.getEmail(),
                            customer.getFirstName(),
                            customer.getLastName()
                    )
            );

            return convertToExisting(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new InvalidCustomerDataException("Invalid customer data provided");
        }
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
    public ExistingCustomer update(CustomerUpdate update) throws InvalidCustomerDataException, NotFoundException {
        var customerFound = repo.findById(UUID.fromString(update.getForId()));
        if (customerFound.isEmpty()) {
            throw new NotFoundException("Customer not found", update.getForId());
        }
        var customer = customerFound.get();
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
    public void delete(String id) throws NotFoundException {
        try {
            repo.deleteById(UUID.fromString(id));
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("Customer not found", id);
        }
    }
}
