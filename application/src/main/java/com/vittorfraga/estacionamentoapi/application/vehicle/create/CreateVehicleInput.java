package com.vittorfraga.estacionamentoapi.application.vehicle.create;

import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;

public record CreateVehicleInput(String brand,
                                 String model,
                                 String licensePlate,
                                 String color,
                                 VehicleType type
) {
    public static CreateVehicleInput build(String brand, String model, String licensePlate, String color, VehicleType type) {
        return new CreateVehicleInput(brand, model, licensePlate, color, type);
    }
}
