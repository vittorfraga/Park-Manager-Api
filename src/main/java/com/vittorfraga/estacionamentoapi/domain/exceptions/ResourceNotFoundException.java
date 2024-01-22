package com.vittorfraga.estacionamentoapi.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String objectName, String id) {
        super("Could not find " + objectName + " with Id " + id);
    }

    public ResourceNotFoundException(String objectName, Long id) {
        super("Could not find " + objectName + " with Id " + id);
    }

}