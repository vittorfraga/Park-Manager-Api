package com.vittorfraga.estacionamentoapi.application.establishment.retrieve.get;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentGateway;
import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;

import java.util.Objects;
import java.util.function.Supplier;


public class GetEstablishmentByIdUseCaseImpl implements GetEstablishmentByIdUseCase {

    private final EstablishmentGateway gateway;

    public GetEstablishmentByIdUseCaseImpl(final EstablishmentGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway, "gateway should not be null");
    }
    
    @Override
    public Establishment execute(GetEstablishmentByIdInput anInput) {

        final var anEstablishmentID = anInput.id();

        return this.gateway.findById(anEstablishmentID).orElseThrow(notFound(anEstablishmentID));


    }

    private static Supplier<DomainException> notFound(String anId) {
        return () -> new DomainException("Establishment", "with ID " + anId + " was not found");
    }


}