package com.vittorfraga.estacionamentoapi.application.establishment.create;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentGateway;

import java.util.Objects;


public class CreateEstablishmentUseCaseImpl implements CreateEstablishmentUseCase {

    private final EstablishmentGateway gateway;

    public CreateEstablishmentUseCaseImpl(final EstablishmentGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway, "gateway should not be null");
    }


    @Override
    public Establishment execute(CreateEstablishmentInput anInput) {

        final var name = anInput.name();
        final var cnpj = anInput.cnpj();
        final var address = anInput.address();
        final var phone = anInput.phone();
        final var motorcycleSlots = anInput.motorcycleSlots();
        final var carSlots = anInput.carSlots();

        final var establishment = Establishment.builder(
                name,
                cnpj,
                address,
                phone,
                motorcycleSlots,
                carSlots
        );
        
        return this.gateway.create(establishment);
    }
}