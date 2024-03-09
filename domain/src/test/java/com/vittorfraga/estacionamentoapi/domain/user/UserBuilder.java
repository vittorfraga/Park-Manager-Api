package com.vittorfraga.estacionamentoapi.domain.user;

import com.vittorfraga.estacionamentoapi.domain.validation.handler.ThrowsValidationHandler;

public class UserBuilder {
    private String username = "userteste";
    private String password = "123456";
    private String name = "User Teste";
    private String email = "user-teste@email.com";


    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public User build() {
        User user = User.builder(username, password, name, email);
        user.validate(new ThrowsValidationHandler());
        return user;
    }


    @Override
    public String toString() {
        return "UserBuilder{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
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
}
