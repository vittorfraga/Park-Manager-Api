package com.vittorfraga.estacionamentoapi.domain.exceptions.vehicle;

public class DuplicateLicensePlateException extends RuntimeException {

    public DuplicateLicensePlateException() {
        super("License plate already exists");
    }
}

