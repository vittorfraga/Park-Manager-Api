package com.vittorfraga.estacionamentoapi.domain.exceptions.parkingaccess;

public class NoAvailableSlotsException extends RuntimeException {

    public NoAvailableSlotsException(String vehicleType) {
        super("No available " + vehicleType.toLowerCase() + " slots.");
    }
}
