package com.vittorfraga.estacionamentoapi.domain.exceptions.vehicle;

public class LicensePlateNotFoundException extends RuntimeException {

    public LicensePlateNotFoundException(String licensePlate) {
        super("License plate " + licensePlate + " not found");
    }
}
