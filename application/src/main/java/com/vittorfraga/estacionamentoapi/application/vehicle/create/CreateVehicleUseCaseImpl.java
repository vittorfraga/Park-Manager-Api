package com.vittorfraga.estacionamentoapi.application.vehicle.create;


import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleGateway;

import java.util.Objects;
import java.util.Optional;

public class CreateVehicleUseCaseImpl implements CreateVehicleUseCase {

    private final VehicleGateway gateway;

    public CreateVehicleUseCaseImpl(final VehicleGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway, "gateway should not be null");
    }


    @Override
    public Vehicle execute(CreateVehicleInput anInput) {

        final var brand = anInput.brand();
        final var model = anInput.model();
        final var licensePlate = anInput.licensePlate();
        final var color = anInput.color();
        final var type = anInput.type();

        Vehicle vehicle = Vehicle.builder(
                brand,
                model,
                licensePlate,
                color,
                type);


        Optional<Vehicle> licensePlateExists = this.gateway.findByLicensePlate(licensePlate);

        if (licensePlateExists.isPresent()) {
            throw new DomainException("License plate already exists");
        }

        return this.gateway.create(vehicle);
    }
}
