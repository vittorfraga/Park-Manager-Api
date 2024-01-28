package com.vittorfraga.estacionamentoapi.domain.exceptions.parkingaccess;

public class VehicleMustExitException extends RuntimeException {

    public VehicleMustExitException() {
        super("The vehicle must exit before making an entrance.");
    }
}
