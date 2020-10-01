package com.microecom.customerservice.model.storage;

import com.microecom.customerservice.model.data.Address;
import com.microecom.customerservice.model.data.AddressUpdate;
import com.microecom.customerservice.model.data.ExistingAddress;
import com.microecom.customerservice.model.exception.InvalidAddressDataException;
import com.microecom.customerservice.model.exception.NotFoundException;
import com.microecom.customerservice.model.storage.address.AddressCrudRepository;
import com.microecom.customerservice.model.storage.address.ComposedAddressRepository;
import com.microecom.customerservice.model.storage.address.data.AddressRow;
import com.microecom.customerservice.model.storage.address.data.Existing;
import com.microecom.customerservice.model.storage.customer.CustomerCrudRepository;
import com.microecom.customerservice.model.storage.data.AddressListCriteria;
import com.microecom.customerservice.model.storage.data.MutableAddressListCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaAddressRepository implements AddressRepository {
    private final ComposedAddressRepository repo;

    private final CustomerCrudRepository customerRepo;

    public JpaAddressRepository(@Autowired ComposedAddressRepository repo, @Autowired CustomerCrudRepository customerRepo) {
        this.repo = repo;
        this.customerRepo = customerRepo;
    }

    @Override
    public ExistingAddress create(Address newAddress) {
        String line2;
        if (newAddress.getAddressLine2().isPresent()) {
            line2 = newAddress.getAddressLine2().get();
        } else {
            line2 = null;
        }
        var customerFound = customerRepo.findById(UUID.fromString(newAddress.getCustomerId()));
        if (customerFound.isEmpty()) {
            throw new InvalidAddressDataException("Wrong customer link");
        }

        return convertToExisting(
                repo.save(
                        new AddressRow(
                                customerFound.get(),
                                newAddress.getAddressLine(),
                                line2,
                                newAddress.getZipCode()
                        )
                )
        );
    }

    @Override
    public Optional<ExistingAddress> findById(String id) {
        return repo.findById(UUID.fromString(id)).map(this::convertToExisting);
    }

    @Override
    public List<ExistingAddress> findByCustomerId(String id) throws NotFoundException {
        return findByCustomerId(id, new MutableAddressListCriteria());
    }

    @Override
    public List<ExistingAddress> findByCustomerId(String id, AddressListCriteria criteria) throws NotFoundException {
        var customerFound = customerRepo.findById(UUID.fromString(id));
        if (customerFound.isEmpty()) {
            throw new NotFoundException("Invalid customer ID given");
        }

        var list = repo.findByCustomerId(UUID.fromString(id), criteria);
        var result = new ArrayList<ExistingAddress>();
        for (AddressRow row : list) {
            result.add(convertToExisting(row));
        }

        return result;
    }

    @Override
    public ExistingAddress update(AddressUpdate update) throws InvalidAddressDataException {
        var existingRowFound = repo.findById(UUID.fromString(update.getForId()));
        if (existingRowFound.isEmpty()) {
            throw new InvalidAddressDataException("Address with given ID not found");
        }
        var row = existingRowFound.get();

        if (update.getAddressLine().isPresent()) {
            row.setAddressLine(update.getAddressLine().get());
        }
        if (update.getAddressLine2().isPresent()) {
            row.setAddressLine2(update.getAddressLine2().get());
        }
        if (update.getZipCode().isPresent()) {
            row.setZipCode(update.getZipCode().get());
        }

        return convertToExisting(repo.save(row));
    }

    @Override
    public void delete(String id) throws NotFoundException {
        try {
            repo.deleteById(UUID.fromString(id));
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("Address not found", id);
        }
    }

    private ExistingAddress convertToExisting(AddressRow row) {
        String line2;
        if (row.getAddressLine2().isEmpty()) {
            line2 = null;
        } else {
            line2 = row.getAddressLine2().get();
        }
        return new Existing(
                row.getId().toString(),
                row.getCustomer().getId().toString(),
                row.getAddressLine(),
                line2,
                row.getZipCode()
        );
    }
}
