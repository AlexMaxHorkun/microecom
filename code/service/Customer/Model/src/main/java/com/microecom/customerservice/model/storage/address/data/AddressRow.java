package com.microecom.customerservice.model.storage.address.data;

import com.microecom.customerservice.model.storage.customer.data.CustomerRow;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "addresses")
public class AddressRow {
    @Id
    private UUID id;

    private String addressLine;

    @Column(name = "address_line2")
    private String addressLine2;

    private int zipCode;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private CustomerRow customer;

    public AddressRow(CustomerRow customer, String addressLine, String addressLine2, int zipCode) {
        this.addressLine = addressLine;
        this.addressLine2 = addressLine2;
        this.zipCode = zipCode;
        id = UUID.randomUUID();
        this.customer = customer;
    }

    protected AddressRow() {}

    public UUID getId() {
        return id;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public Optional<String> getAddressLine2() {
        if (addressLine2 == null) {
            return Optional.empty();
        }
        return Optional.of(addressLine2);
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public CustomerRow getCustomer() {
        return customer;
    }
}
