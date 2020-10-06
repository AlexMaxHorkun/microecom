package com.microecom.authservice.model.storage.jpa.data;

import javax.persistence.*;
import java.util.UUID;

/**
 * Authentication data for users that can log-in with login and password.
 */
@Entity
@Table(name="credentials_auth")
public class CredentialsAuthRow {
    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserRow user;

    private String login;

    @Column(name = "passwd")
    private String password;

    protected CredentialsAuthRow () {}

    public CredentialsAuthRow(UserRow user, String login, String password) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.login = login;
        this.password = password;
    }

    public UserRow getUser() {
        return user;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
