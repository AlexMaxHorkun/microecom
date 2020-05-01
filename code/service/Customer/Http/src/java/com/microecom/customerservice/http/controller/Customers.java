package com.microecom.customerservice.http.controller;

import com.microecom.customerservice.http.controller.data.FullCustomerRead;
import com.microecom.customerservice.http.controller.data.NewCustomer;
import com.microecom.customerservice.model.CustomerManager;
import com.microecom.customerservice.model.Registration;
import com.microecom.customerservice.model.client.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/rest/V1/customer")
public class Customers {
    private final Registration registrationService;

    private final AuthClient authClient;

    private final CustomerManager customers;

    public Customers(
            @Autowired Registration registrationService,
            @Autowired AuthClient authClient,
            @Autowired CustomerManager customers
    ) {
        this.registrationService = registrationService;
        this.authClient = authClient;
        this.customers = customers;
    }

    @PostMapping
    public ResponseEntity<FullCustomerRead> register(@RequestBody @Valid NewCustomer registering) {
        var created = registrationService.register(registering);

        return new ResponseEntity<>(
                new FullCustomerRead(
                        created.getEmail(),
                        created.getFirstName(),
                        created.getId(),
                        registering.getLogin(),
                        created.getLastName()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<FullCustomerRead> me(Principal principal) {
        return ResponseEntity.notFound().build();
    }
}
