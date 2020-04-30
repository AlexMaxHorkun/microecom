package com.microecom.customerservice.model;

import com.microecom.customerservice.model.data.ExistingCustomer;
import com.microecom.customerservice.model.storage.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerManagerService implements CustomerManager {
    private final CustomerRepository repo;

    public CustomerManagerService(@Autowired CustomerRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<ExistingCustomer> findByUserId(String id) {
        return repo.findByUserId(id);
    }
}
