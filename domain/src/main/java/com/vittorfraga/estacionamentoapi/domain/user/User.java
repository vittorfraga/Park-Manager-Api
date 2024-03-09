package com.vittorfraga.estacionamentoapi.domain.user;


import com.vittorfraga.estacionamentoapi.domain.validation.ValidationHandler;

import java.util.UUID;

public class User {

    private final String id;
    private String username;
    private String password;
    private String name;
    private String email;

    private User(String id, String username, String password, String name, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;

    }

    public static User builder(
            final String username,
            final String password,
            final String name,
            final String email) {
        return new User(UUID.randomUUID().toString(), username, password, name, email);
    }

    public static User with(
            final String id,
            final String username,
            final String password,
            final String name,
            final String email) {
        return new User(id, username, password, name, email);
    }


    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public User update(final String username, final String password, final String name, final String email) {
        this.username = (username != null) ? username : this.username;
        this.password = (password != null) ? password : this.password;
        this.name = (name != null) ? name : this.name;
        this.email = (email != null) ? email : this.email;

        return this;
    }

    public void validate(final ValidationHandler handler) {
        new UserValidator(this, handler).validate();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
