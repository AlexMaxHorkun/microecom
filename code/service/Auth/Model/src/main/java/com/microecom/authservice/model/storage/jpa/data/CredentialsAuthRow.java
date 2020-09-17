package com.microecom.authservice.model.storage.jpa.data;

import javax.persistence.*;

/**
 * Authentication data for users that can log-in with login and password.
 */
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

    public CredentialsAuthRow(UserRow user, String login, String password) {
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
