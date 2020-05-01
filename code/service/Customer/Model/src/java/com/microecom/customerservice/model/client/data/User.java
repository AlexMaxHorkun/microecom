package com.microecom.customerservice.model.client.data;

import java.time.Instant;
import java.util.Optional;

public class User {
    private final String login;

    private final String id;

    private final Instant created;

    public User(String login, String id, Instant created) {
        this.login = login;
        this.id = id;
        this.created = created;
    }

    public User(String id, Instant created) {
        this.id = id;
        this.created = created;
        login = null;
    }

    public Optional<String> getLogin() {
        if (login == null) {
            return Optional.empty();
        }

        return Optional.of(login);
    }

    public String getId() {
        return id;
    }

    public Instant getCreated() {
        return created;
    }
}
