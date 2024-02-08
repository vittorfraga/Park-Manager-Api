package com.vittorfraga.estacionamentoapi.application.establishment.delete;


import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentGateway;
import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;

import java.util.Objects;
import java.util.function.Supplier;

public class DeleteEstablishmentUseCaseImpl implements DeleteEstablishmentUseCase {

    private final EstablishmentGateway gateway;

    public DeleteEstablishmentUseCaseImpl(final EstablishmentGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway, "gateway should not be null");
    }

    @Override
    public void execute(DeleteEstablishmentInput anInput) {
        final var foundEstablishment = this.gateway.findById(anInput.id()).orElseThrow(notFound(anInput.id()));

        this.gateway.delete(foundEstablishment.getId());
    }

    private static Supplier<DomainException> notFound(String anId) {
        return () -> new DomainException("Establishment", "with ID " + anId + " was not found");
    }
    
}
