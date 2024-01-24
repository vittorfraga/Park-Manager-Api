package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VehicleTestHelper {

    public static Vehicle createAndSaveVehicle(VehicleRepository repository) {
        final var expectedBrand = "Brand";
        final var expectedModel = "Model";
        final var expectedLicensePlate = "31A2B3";
        final var expectedColor = "verde";
        final var expectedType = VehicleType.CAR;

        final var aVehicle = new Vehicle(expectedBrand, expectedModel, expectedLicensePlate, expectedColor, expectedType);

        when(repository.save(any(Vehicle.class))).thenAnswer(invocation -> {
            Vehicle vehicleToSave = invocation.getArgument(0);
            return new Vehicle(1L, vehicleToSave.getBrand(), vehicleToSave.getModel(), vehicleToSave.getLicensePlate(), vehicleToSave.getColor(), vehicleToSave.getType());
        });

        return repository.save(aVehicle);
    }
}