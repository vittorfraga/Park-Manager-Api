package com.vittorfraga.estacionamentoapi.application.user.create;

public record CreateUserInput(
        String username,
        String password,
        String name,
        String email
) {
    public static CreateUserInput builder(
            final String username,
            final String password,
            final String name,
            final String email) {
        return new CreateUserInput(username, password, name, email);
    }
}
