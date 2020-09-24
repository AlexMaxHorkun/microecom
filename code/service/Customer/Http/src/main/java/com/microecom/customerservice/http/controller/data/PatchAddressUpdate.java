package com.microecom.customerservice.http.controller.data;

import com.microecom.customerservice.model.data.AddressUpdate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Optional;

public class PatchAddressUpdate implements AddressUpdate {
    private String id;

    private final String addressLine;

    private final String addressLine2;

    private final Integer zipCode;

    public PatchAddressUpdate(String addressLine, String addressLine2, Integer zipCode) {
        this.addressLine = addressLine;
        this.addressLine2 = addressLine2;
        this.zipCode = zipCode;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getForId() {
        return id;
    }

    @Override
    public Optional<@Size(min = 5, max = 255)  String> getAddressLine() {
        return Optional.ofNullable(addressLine);
    }

    @Override
    public Optional<@Size(min = 5, max = 255) String> getAddressLine2() {
        return Optional.ofNullable(addressLine2);
    }

    @Override
    public Optional<@Min(value = 0) @Max(value = 99999) Integer> getZipCode() {
        return Optional.ofNullable(zipCode);
    }
}
