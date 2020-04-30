package com.microecom.authservice.model.storage.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

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

    public UserRow(@NotNull Instant timestamp) {
        created = timestamp;
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public @NotNull Instant getCreated() {
        return created;
    }

    public Optional<CredentialsAuthRow> getCredentialsAuthRow() {
        if (credentialsAuthRow != null)
            return Optional.of(credentialsAuthRow);
        else
            return Optional.empty();
    }
}
