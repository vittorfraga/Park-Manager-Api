package com.vittorfraga.estacionamentoapi.domain.vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleGateway {
    Vehicle create(Vehicle aVehicle);

    Vehicle update(Vehicle aVehicle);

    Optional<Vehicle> findById(String anId);

    List<Vehicle> findAll();

    void deleteById(String anId);
}
