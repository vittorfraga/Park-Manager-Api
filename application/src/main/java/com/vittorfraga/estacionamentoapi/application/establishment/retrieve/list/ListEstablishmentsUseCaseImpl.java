package com.vittorfraga.estacionamentoapi.application.establishment.retrieve.list;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentGateway;

import java.util.List;
import java.util.Objects;


public class ListEstablishmentsUseCaseImpl implements ListEstablishmentsUseCase {

    private final EstablishmentGateway gateway;

    public ListEstablishmentsUseCaseImpl(final EstablishmentGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway, "gateway should not be null");
    }

    @Override
    public List<Establishment> execute() {
        return gateway.findAll();
    }
}