package com.vittorfraga.estacionamentoapi.domain.vehicle;

import com.vittorfraga.estacionamentoapi.domain.Validator;

public class VehicleValidator {

    public static void validateVehicleFields(Vehicle vehicle) {
        Validator.validateNotNullOrBlank(vehicle.getBrand(), "brand");
        Validator.validateNotNullOrBlank(vehicle.getModel(), "model");
        Validator.validateNotNullOrBlank(vehicle.getColor(), "color");
        Validator.validateNotNullOrBlank(vehicle.getLicensePlate(), "licensePlate");
        Validator.validateNotNull(vehicle.getType(), "type");
    }
}

