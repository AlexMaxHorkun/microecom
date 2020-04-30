package com.microecom.customerservice.model.client.data;

import java.time.Instant;

public class User {
    private final String login;

    private final String id;

    private final Instant created;

    public User(String login, String id, Instant created) {
        this.login = login;
        this.id = id;
        this.created = created;
    }

    public String getLogin() {
        return login;
    }

    public String getId() {
        return id;
    }

    public Instant getCreated() {
        return created;
    }
}
