package com.microecom.customerservice.http.controller;

import com.microecom.customerservice.http.controller.data.FullCustomerRead;
import com.microecom.customerservice.http.controller.data.NewCustomer;
import com.microecom.customerservice.http.controller.data.SelfUpdate;
import com.microecom.customerservice.http.model.PrincipalManager;
import com.microecom.customerservice.model.CustomerManager;
import com.microecom.customerservice.model.Registration;
import com.microecom.customerservice.model.client.AuthClient;
import com.microecom.customerservice.model.data.ExistingCustomer;
import com.microecom.util.faultinjection.aop.annotation.InjectDelay;
import com.microecom.util.faultinjection.aop.annotation.InjectExceptionAfter;
import com.microecom.util.faultinjection.aop.annotation.InjectExceptionBefore;
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

    private final PrincipalManager principals;

    public Customers(
            @Autowired Registration registrationService,
            @Autowired AuthClient authClient,
            @Autowired CustomerManager customers,
            @Autowired PrincipalManager principalManager
    ) {
        this.registrationService = registrationService;
        this.authClient = authClient;
        this.customers = customers;
        this.principals = principalManager;
    }

    @PostMapping
    @InjectExceptionAfter
    @InjectDelay
    public ResponseEntity<FullCustomerRead> register(@RequestBody @Valid NewCustomer registering) {
        var created = registrationService.register(registering);

        return new ResponseEntity<>(fetchFullRead(created, registering.getLogin()), HttpStatus.CREATED);
    }

    @GetMapping(path = "/me")
    @InjectExceptionBefore
    @InjectDelay
    public ResponseEntity<FullCustomerRead> me(Principal principal) {
        var customer = principals.extractCustomer(principal).get();

        return new ResponseEntity<>(fetchFullRead(customer), HttpStatus.OK);
    }

    @PatchMapping(path = "/me")
    @InjectExceptionAfter
    @InjectDelay
    public ResponseEntity<FullCustomerRead> updateMe(@RequestBody @Valid SelfUpdate update, Principal principal) {
        var customer = principals.extractCustomer(principal);
        update.setId(customer.get().getId());

        return new ResponseEntity<>(fetchFullRead(customers.update(update)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/me")
    @InjectExceptionAfter
    @InjectDelay
    public ResponseEntity<Object> delete(Principal principal) {
        customers.delete(principals.extractCustomer(principal).get().getId());

        return ResponseEntity.noContent().build();
    }

    private FullCustomerRead fetchFullRead(ExistingCustomer customerRecord) {
        var userInfo = authClient.get(customerRecord.getUserId()).get();
        String login = null;
        if (userInfo.getLogin().isPresent()) {
            login = userInfo.getLogin().get();
        }

        return fetchFullRead(customerRecord, login);
    }

    private FullCustomerRead fetchFullRead(ExistingCustomer customerRecord, String login) {
        String shipId = null;
        if (customerRecord.getDefaultShippingAddress().isPresent()) {
            shipId = customerRecord.getDefaultShippingAddress().get();
        }
        String billId = null;
        if (customerRecord.getDefaultBillingAddress().isPresent()) {
            billId = customerRecord.getDefaultBillingAddress().get();
        }

        return new FullCustomerRead(
                customerRecord.getEmail(),
                customerRecord.getFirstName(),
                customerRecord.getId(),
                customerRecord.getLastName(),
                login,
                shipId,
                billId
        );
    }
}
