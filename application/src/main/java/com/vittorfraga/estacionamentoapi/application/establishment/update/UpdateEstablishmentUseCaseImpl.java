package com.vittorfraga.estacionamentoapi.application.establishment.update;


import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentGateway;
import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;

import java.util.Objects;
import java.util.function.Supplier;


public class UpdateEstablishmentUseCaseImpl implements UpdateEstablishmentUseCase {

    private final EstablishmentGateway gateway;

    public UpdateEstablishmentUseCaseImpl(final EstablishmentGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway, "gateway should not be null");
    }


    @Override
    public Establishment execute(UpdateEstablishmentInput anInput) {


        final var id = anInput.id();
        final var name = anInput.name();
        final var cnpj = anInput.cnpj();
        final var address = anInput.address();
        final var phone = anInput.phone();
        final var motorcycleSlots = anInput.motorcycleSlots();
        final var carSlots = anInput.carSlots();

        final var actualEstablishment = this.gateway.findById(id)
                .orElseThrow(notFound(id));


        actualEstablishment.update(name, cnpj, address, phone, motorcycleSlots, carSlots);


        Establishment updatedEstablishment = this.gateway.update(actualEstablishment);

        if (updatedEstablishment == null) {
            throw notFound(id).get();
        }

        return updatedEstablishment;
    }

    private static Supplier<DomainException> notFound(String anId) {
        return () -> new DomainException("Establishment", "with ID " + anId + " was not found");
    }
}
