package com.microecom.customerservice.model;

import com.microecom.customerservice.model.client.AuthClient;
import com.microecom.customerservice.model.client.data.NewUser;
import com.microecom.customerservice.model.client.exception.InvalidUserDataException;
import com.microecom.customerservice.model.data.ExistingCustomer;
import com.microecom.customerservice.model.data.SigningUp;
import com.microecom.customerservice.model.exception.InvalidCustomerDataException;
import com.microecom.customerservice.model.registration.data.NewCustomer;
import com.microecom.customerservice.model.storage.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService implements Registration {
    private final AuthClient authClient;

    private final CustomerRepository repo;

    public RegistrationService(@Autowired AuthClient authClient, @Autowired CustomerRepository repo) {
        this.authClient = authClient;
        this.repo = repo;
    }

    @Override
    public ExistingCustomer register(SigningUp newCustomer) throws InvalidCustomerDataException {
        try {
            var user = authClient.create(new NewUser(newCustomer.getLogin(), newCustomer.getPassword()));
            //User might've been already created if this is a duplicate request
            var existing = repo.findByUserId(user.getId());
            if (existing.isPresent()) {
                return existing.get();
            }

            //Actually a new user
            return repo.save(
                    new NewCustomer(
                            newCustomer.getFirstName(),
                            newCustomer.getLastName(),
                            newCustomer.getEmail(),
                            user.getId()
                    )
            );
        } catch (InvalidUserDataException ex) {
            if (ex.getFieldViolations().isPresent()) {
                throw new InvalidCustomerDataException(ex.getFieldViolations().get());
            } else {
                throw new InvalidCustomerDataException(ex.getMessage());
            }
        }
    }
}
