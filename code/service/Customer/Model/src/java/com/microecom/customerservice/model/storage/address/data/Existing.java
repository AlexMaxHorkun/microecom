package com.microecom.customerservice.model.storage.address.data;

import com.microecom.customerservice.model.data.ExistingAddress;

import java.util.Optional;

public class Existing implements ExistingAddress {
    private final String id;

    private final String customerId;

    private final String addressLine;

    private final String addressLine2;

    private final int zipCode;

    public Existing(String id, String customerId, String addressLine, String addressLine2, int zipCode) {
        this.id = id;
        this.customerId = customerId;
        this.addressLine = addressLine;
        this.addressLine2 = addressLine2;
        this.zipCode = zipCode;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String getAddressLine() {
        return addressLine;
    }

    @Override
    public Optional<String> getAddressLine2() {
        return Optional.ofNullable(addressLine2);
    }

    @Override
    public int getZipCode() {
        return zipCode;
    }
}
