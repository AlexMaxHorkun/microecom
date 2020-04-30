package com.microecom.authservice.model.storage.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="credentials_auth")
public class CredentialsAuthRow {
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserRow user;

    @Id
    private String login;

    @Column(name = "passwd")
    private String password;

    protected CredentialsAuthRow () {}

    public CredentialsAuthRow(@NotNull UserRow user, @NotNull String login, @NotNull String password) {
        this.user = user;
        this.login = login;
        this.password = password;
    }

    public @NotNull UserRow getUser() {
        return user;
    }

    public @NotNull String getLogin() {
        return login;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }
}
