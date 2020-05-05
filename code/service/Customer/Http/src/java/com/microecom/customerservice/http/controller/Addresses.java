package com.microecom.customerservice.http.controller;

import com.microecom.customerservice.http.controller.data.AddressRead;
import com.microecom.customerservice.http.controller.data.NewAddress;
import com.microecom.customerservice.http.controller.data.PatchAddressUpdate;
import com.microecom.customerservice.http.model.PrincipalManager;
import com.microecom.customerservice.model.AddressManager;
import com.microecom.customerservice.model.data.ExistingAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/rest/V1/address")
public class Addresses {
    private final AddressManager addresses;

    private final PrincipalManager principals;

    public Addresses(@Autowired AddressManager addresses, @Autowired PrincipalManager principalManager) {
        this.addresses = addresses;
        this.principals = principalManager;
    }

    @PostMapping
    public ResponseEntity<AddressRead> create(@RequestBody @Valid NewAddress address, Principal principal) {
        var customerFound = principals.extractCustomer(principal);
        address.setCustomerId(customerFound.get().getId());

        return new ResponseEntity<>(AddressRead.of(addresses.create(address)), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<AddressRead> update(
            @RequestBody @Valid PatchAddressUpdate update,
            @PathVariable @NotNull String id,
            Principal principal
    ) {
        var addressFound = addresses.findById(id);
        var customerFound = principals.extractCustomer(principal);
        if (addressFound.isEmpty() || !addressFound.get().getCustomerId().equals(customerFound.get().getId())) {
            return ResponseEntity.notFound().build();
        }
        update.setId(id);

        return new ResponseEntity<>(AddressRead.of(addresses.update(update)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable @NotNull String id, Principal principal) {
        var addressFound = addresses.findById(id);
        var customer = principals.extractCustomer(principal).get();
        if (addressFound.isEmpty() || !addressFound.get().getCustomerId().equals(customer.getId())) {
            return ResponseEntity.notFound().build();
        }

        addresses.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AddressRead>> list(Principal principal) {
        var customer = principals.extractCustomer(principal).get();
        var found = addresses.findListFor(customer.getId());
        List<AddressRead> list = new ArrayList<>();
        for (ExistingAddress address : found) {
            list.add(AddressRead.of(address));
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
