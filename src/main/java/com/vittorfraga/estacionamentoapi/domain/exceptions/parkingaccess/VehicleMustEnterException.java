package com.vittorfraga.estacionamentoapi.domain.exceptions.parkingaccess;

public class VehicleMustEnterException extends RuntimeException {

    public VehicleMustEnterException() {
        super("The vehicle must enter before making an exit.");
    }
}
