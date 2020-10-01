package com.microecom.customerservice.model.storage.data;

import java.util.Optional;

public class MutableAddressListCriteria implements AddressListCriteria {
    private String addressLineEq;
    private String addressLine2Eq;
    private Boolean addressLine2IsNull;
    private Integer zipCodeEq;

    public MutableAddressListCriteria() {}

    @Override
    public Optional<String> getAddressLineEq() {
        return Optional.ofNullable(addressLineEq);
    }

    @Override
    public Optional<String> getAddressLine2Eq() {
        return Optional.ofNullable(addressLine2Eq);
    }

    @Override
    public Optional<Boolean> getAddressLine2IsNull() {
        return Optional.ofNullable(addressLine2IsNull);
    }

    @Override
    public Optional<Integer> getZipCodeEq() {
        return Optional.ofNullable(zipCodeEq);
    }

    public void setAddressLineEq(String addressLineEq) {
        this.addressLineEq = addressLineEq;
    }

    public void setAddressLine2Eq(String addressLine2Eq) {
        this.addressLine2Eq = addressLine2Eq;
    }

    public void setAddressLine2IsNull(Boolean addressLine2IsNull) {
        this.addressLine2IsNull = addressLine2IsNull;
    }

    public void setZipCodeEq(Integer zipCodeEq) {
        this.zipCodeEq = zipCodeEq;
    }
}
