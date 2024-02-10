package com.vittorfraga.estacionamentoapi.domain.exceptions;

import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;

public class NoAvailableSlotsException extends RuntimeException {

    public NoAvailableSlotsException(VehicleType vehicleType) {
        super("No available " + vehicleType.toString().toLowerCase() + " slots");
    }
}
