package com.microecom.customerservice.http.controller.data;

import com.microecom.customerservice.model.data.Address;

import javax.validation.constraints.*;
import java.util.Optional;

public class NewAddress implements Address {
    private final String customerId;

    private final String addressLine;

    private final String addressLine2;

    private final int zipCode;

    public NewAddress(String customerId, String addressLine, String addressLine2, Integer zipCode) {
        this.customerId = customerId;
        this.addressLine = addressLine;
        this.addressLine2 = addressLine2;
        this.zipCode = zipCode;
    }

    @Override
    public @NotNull @NotBlank String getCustomerId() {
        return customerId;
    }

    @Override
    public @NotNull @NotBlank @Size(min = 5, max = 255) String getAddressLine() {
        return addressLine;
    }

    @Override
    public @NotBlank @Size(min = 5, max = 255) Optional<String> getAddressLine2() {
        return Optional.ofNullable(addressLine2);
    }

    @Override
    public @Min(value = 0) @Max(value = 99999) int getZipCode() {
        return zipCode;
    }
}
