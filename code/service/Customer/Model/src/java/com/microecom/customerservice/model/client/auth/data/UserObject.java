package com.microecom.customerservice.model.client.auth.data;

import java.time.Instant;
import java.util.Optional;

public class UserObject {
    private String id;

    private Instant created;

    private String login;

    public UserObject() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Optional<String> getLogin() {
        if (login == null ) {
            return Optional.empty();
        }

        return Optional.of(login);
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
