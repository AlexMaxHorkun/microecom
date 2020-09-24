package com.microecom.customerservice.http.controller.data;

import com.microecom.customerservice.model.data.Address;

import javax.validation.constraints.*;
import java.util.Optional;

public class NewAddress implements Address {
    private String customerId;

    private final String addressLine;

    private final String addressLine2;

    private final int zipCode;

    public NewAddress(String addressLine, String addressLine2, Integer zipCode) {
        this.addressLine = addressLine;
        this.addressLine2 = addressLine2;
        this.zipCode = zipCode;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String getCustomerId() {
        return customerId;
    }

    @Override
    public @NotNull @NotBlank @Size(min = 5, max = 255) String getAddressLine() {
        return addressLine;
    }

    @Override
    public Optional<@Size(min = 5, max = 255)String> getAddressLine2() {
        return Optional.ofNullable(addressLine2);
    }

    @Override
    public @Min(value = 0) @Max(value = 99999) int getZipCode() {
        return zipCode;
    }
}
