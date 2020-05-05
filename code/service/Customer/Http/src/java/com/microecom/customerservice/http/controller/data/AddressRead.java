package com.microecom.customerservice.http.controller.data;

import com.microecom.customerservice.model.data.ExistingAddress;

import java.util.Optional;

public class AddressRead {
    private final String id;

    private final String addressLine;

    private final String addressLine2;

    private final int zipCode;

    public static AddressRead of(ExistingAddress address) {
        String line2 = null;
        if (address.getAddressLine2().isPresent()) {
            line2 = address.getAddressLine2().get();
        }
        return new AddressRead(
                address.getId(),
                address.getAddressLine(),
                line2,
                address.getZipCode()
        );
    }

    public AddressRead(String id, String addressLine, String addressLine2, int zipCode) {
        this.id = id;
        this.addressLine = addressLine;
        this.addressLine2 = addressLine2;
        this.zipCode = zipCode;
    }

    public String getId() {
        return id;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public Optional<String> getAddressLine2() {
        return Optional.ofNullable(addressLine2);
    }

    public int getZipCode() {
        return zipCode;
    }
}
