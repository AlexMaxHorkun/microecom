package com.microecom.customerservice.model;

import com.microecom.customerservice.model.data.Address;
import com.microecom.customerservice.model.data.AddressUpdate;
import com.microecom.customerservice.model.data.ExistingAddress;
import com.microecom.customerservice.model.exception.InvalidAddressDataException;
import com.microecom.customerservice.model.exception.NotFoundException;
import com.microecom.customerservice.model.storage.AddressRepository;
import com.microecom.customerservice.model.storage.data.MutableAddressListCriteria;
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
    public ExistingAddress create(Address newAddress) throws InvalidAddressDataException {
        //Have to check if the exact same address exist in case of a duplicate request
        var criteria = new MutableAddressListCriteria();
        criteria.setAddressLineEq(newAddress.getAddressLine());
        criteria.setZipCodeEq(newAddress.getZipCode());
        if (newAddress.getAddressLine2().isPresent()) {
            criteria.setAddressLine2Eq(newAddress.getAddressLine2().get());
        } else {
            criteria.setAddressLine2IsNull(true);
        }
        var found = repo.findByCustomerId(newAddress.getCustomerId(), criteria);
        if (found.size() == 1) {
            return found.get(0);
        }

        //Creating a new unique one.
        return repo.create(newAddress);
    }

    @Override
    public ExistingAddress update(AddressUpdate update) throws InvalidAddressDataException {
        return repo.update(update);
    }

    @Override
    public void delete(String id) {
        try {
            repo.delete(id);
        } catch (NotFoundException ex) {
            //Possibly a duplicate request, ignoring.
        }
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
