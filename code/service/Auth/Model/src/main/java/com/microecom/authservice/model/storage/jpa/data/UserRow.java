package com.microecom.authservice.model.storage.jpa.data;

import javax.persistence.*;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Authentication agnostic user info.
 */
@Entity
@Table(name="users")
public class UserRow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Instant created;

    @OneToOne(mappedBy = "user")
    private CredentialsAuthRow credentialsAuthRow;

    private String customerEmail;

    private String customerFirstname;

    private String customerLastname;

    private String customerId;

    protected UserRow () {}

    public UserRow(Instant timestamp) {
        created = timestamp;
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public Instant getCreated() {
        return created;
    }

    public Optional<CredentialsAuthRow> getCredentialsAuthRow() {
        return Optional.ofNullable(credentialsAuthRow);
    }

    public void setCredentialsAuthRow(CredentialsAuthRow credentialsAuthRow) {
        this.credentialsAuthRow = credentialsAuthRow;
    }

    public Optional<String> getCustomerEmail() {
        return Optional.ofNullable(customerEmail);
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Optional<String> getCustomerFirstname() {
        return Optional.ofNullable(customerFirstname);
    }

    public void setCustomerFirstname(String customerFirstname) {
        this.customerFirstname = customerFirstname;
    }

    public Optional<String> getCustomerLastname() {
        return Optional.ofNullable(customerLastname);
    }

    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public Optional<String> getCustomerId() {
        return Optional.ofNullable(customerId);
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
