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
        if (credentialsAuthRow != null)
            return Optional.of(credentialsAuthRow);
        else
            return Optional.empty();
    }
}
