package com.vittorfraga.estacionamentoapi.application.vehicle.delete;


import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleGateway;

import java.util.Objects;
import java.util.function.Supplier;


public class DeleteVehicleUseCaseImpl implements DeleteVehicleUseCase {

    private final VehicleGateway gateway;

    public DeleteVehicleUseCaseImpl(VehicleGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public void execute(String anId) {

        if (!gateway.existsById(anId)) {
            throw notFound(anId).get();
        }

        gateway.deleteById(anId);
    }

    private static Supplier<DomainException> notFound(String anId) {
        return () -> new DomainException("Vehicle", "with ID " + anId + " was not found");
    }
}
