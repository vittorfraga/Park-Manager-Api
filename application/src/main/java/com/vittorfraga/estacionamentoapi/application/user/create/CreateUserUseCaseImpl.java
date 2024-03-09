package com.vittorfraga.estacionamentoapi.application.user.create;

import com.vittorfraga.estacionamentoapi.domain.user.User;
import com.vittorfraga.estacionamentoapi.domain.user.UserGateway;

import java.util.Objects;
import java.util.function.Supplier;

public class CreateUserUseCaseImpl implements CreateUserUseCase {


    private final UserGateway gateway;

    public CreateUserUseCaseImpl(UserGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public User execute(CreateUserInput anInput) {
        final var username = anInput.username();
        final var password = anInput.password();
        final var name = anInput.name();
        final var email = anInput.email();


        if (!gateway.findByUsername(username)) {
            throw alreadyExists(username).get();
        }

        User user = User.builder(username, password, name, email);

        return gateway.save(user);
    }

    private static Supplier<DomainException> alreadyExists(String anUsername) {
        return () -> new DomainException(String.format("Username %s already in use", anUsername));
    }

}
