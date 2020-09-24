package com.microecom.customerservice.model;

import com.microecom.customerservice.model.data.Address;
import com.microecom.customerservice.model.data.AddressUpdate;
import com.microecom.customerservice.model.data.ExistingAddress;
import com.microecom.customerservice.model.storage.AddressRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AddressManagerService implements AddressManager {
    private final AddressRepository repo;

    public AddressManagerService(AddressRepository repo) {
        this.repo = repo;
    }

    @Override
    public ExistingAddress create(Address newAddress) throws IllegalArgumentException {
        return repo.create(newAddress);
    }

    @Override
    public ExistingAddress update(AddressUpdate update) throws IllegalArgumentException {
        return repo.update(update);
    }

    @Override
    public void delete(String id) throws IllegalArgumentException {
        repo.delete(id);
    }

    @Override
    public List<ExistingAddress> findListFor(String customerId) {
        return repo.findByCustomerId(customerId);
    }

    @Override
    public Optional<ExistingAddress> findById(String id) {
        return repo.findById(id);
    }
}
