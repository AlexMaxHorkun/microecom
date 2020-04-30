package com.microecom.customerservice.model.storage.customer.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name="customers")
public class CustomerRow {
    @Id
    private UUID id;

    private String userId;

    private String email;

    private String firstName;

    private String lastName;

    private Instant since;

    public CustomerRow(String userId, String email, String firstName, String lastName) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        id = UUID.randomUUID();
        since = ZonedDateTime.now(ZoneOffset.UTC).toInstant();
    }

    protected CustomerRow() {}

    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Instant getSince() {
        return since;
    }
}
