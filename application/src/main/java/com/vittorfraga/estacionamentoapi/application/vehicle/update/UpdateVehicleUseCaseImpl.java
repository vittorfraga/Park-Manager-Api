package com.vittorfraga.estacionamentoapi.application.vehicle.update;

import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleGateway;

import java.util.Objects;
import java.util.function.Supplier;


public class UpdateVehicleUseCaseImpl implements UpdateVehicleUse {

    private final VehicleGateway gateway;

    public UpdateVehicleUseCaseImpl(VehicleGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }


    @Override
    public Vehicle execute(UpdateVehicleInput anInput) {
        final var id = anInput.id();
        final var brand = anInput.brand();
        final var model = anInput.model();
        final var plate = anInput.licensePlate();
        final var color = anInput.color();
        final var type = anInput.type();


        final var foundVehicle = this.gateway.findById(id).orElseThrow(notFound(id));


        foundVehicle.update(brand, model, plate, color, type);


        return this.gateway.update(foundVehicle);
    }

    private static Supplier<DomainException> notFound(String anId) {
        return () -> new DomainException("Vehicle", "with ID " + anId + " was not found");
    }
}
