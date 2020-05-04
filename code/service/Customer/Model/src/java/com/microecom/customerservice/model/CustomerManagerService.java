package com.microecom.customerservice.model;

import com.microecom.customerservice.model.client.AuthClient;
import com.microecom.customerservice.model.data.CustomerUpdate;
import com.microecom.customerservice.model.data.ExistingCustomer;
import com.microecom.customerservice.model.storage.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerManagerService implements CustomerManager {
    private final CustomerRepository repo;

    private final AddressManager addresses;

    private final AuthClient authClient;

    public CustomerManagerService(
            @Autowired CustomerRepository repo,
            @Autowired AddressManager addresses,
            @Autowired AuthClient authClient
    ) {
        this.repo = repo;
        this.addresses = addresses;
        this.authClient = authClient;
    }

    @Override
    public Optional<ExistingCustomer> findByUserId(String id) {
        return repo.findByUserId(id);
    }

    @Override
    public Optional<ExistingCustomer> findById(String id) {
        return repo.findById(id);
    }

    @Override
    public ExistingCustomer update(CustomerUpdate update) throws IllegalArgumentException {
        if (update.getDefaultBillingAddress().isPresent()) {
            validateAddressLink(update.getForId(), update.getDefaultBillingAddress().get());
        }
        if (update.getDefaultShippingAddress().isPresent()) {
            validateAddressLink(update.getForId(), update.getDefaultShippingAddress().get());
        }
        return repo.update(update);
    }

    @Override
    public void delete(String id) throws IllegalArgumentException {
        var customerFound = findById(id);
        if (customerFound.isEmpty()) {
            throw new IllegalArgumentException("Customer with given ID does not exist.");
        }
        authClient.delete(customerFound.get().getUserId());
        repo.delete(id);
    }

    private void validateAddressLink(String customerId, String addressId) throws IllegalArgumentException {
        var found = addresses.findById(addressId);
        if (found.isEmpty() || !found.get().getCustomerId().equals(customerId)) {
            throw new IllegalArgumentException("Invalid address ID given");
        }
    }
}
