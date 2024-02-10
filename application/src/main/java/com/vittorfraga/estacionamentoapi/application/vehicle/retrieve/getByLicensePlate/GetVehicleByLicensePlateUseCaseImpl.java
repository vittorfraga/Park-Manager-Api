package com.vittorfraga.estacionamentoapi.application.vehicle.retrieve.getByLicensePlate;

import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleGateway;

import java.util.Objects;
import java.util.function.Supplier;


public class GetVehicleByLicensePlateUseCaseImpl implements GetVehicleByLicensePlateUseCase {

    private final VehicleGateway gateway;

    public GetVehicleByLicensePlateUseCaseImpl(VehicleGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Vehicle execute(String anInput) {

        return this.gateway.findByLicensePlate(anInput).orElseThrow(notFound(anInput));

    }

    private static Supplier<DomainException> notFound(String anInput) {
        return () -> new DomainException("Vehicle", "with licensePlate " + anInput + " was not found");
    }
}