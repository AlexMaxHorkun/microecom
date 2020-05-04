package com.microecom.customerservice.http.controller;

import com.microecom.customerservice.http.controller.data.AddressRead;
import com.microecom.customerservice.http.controller.data.NewAddress;
import com.microecom.customerservice.model.AddressManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rest/V1/address")
public class Addresses {
    private final AddressManager addresses;

    public Addresses(@Autowired AddressManager addresses) {
        this.addresses = addresses;
    }

    @PostMapping
    public ResponseEntity<AddressRead> create(@RequestBody NewAddress address) {
        return new ResponseEntity<>(AddressRead.of(addresses.create(address)), HttpStatus.CREATED);
    }
}
