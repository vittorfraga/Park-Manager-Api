package com.vittorfraga.estacionamentoapi.application.vehicle.update;

import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;

public record UpdateVehicleInput(
        String id,
        String brand,
        String model,
        String licensePlate,
        String color,
        VehicleType type
) {
    public static UpdateVehicleInput builder(
            String id,
            String brand,
            String model,
            String licensePlate,
            String color,
            VehicleType type
    ) {
        return new UpdateVehicleInput(id, brand, model, licensePlate, color, type);
    }
}
