package com.vittorfraga.estacionamentoapi.domain.exceptions;

public class DomainException extends RuntimeException {

    private final String fieldName;

    public DomainException(String fieldName, String message) {
        super(fieldName + " " + message);
        this.fieldName = fieldName;
    }

    public DomainException(String message) {
        super(message);
        this.fieldName = "";
    }

    public String getFieldName() {
        return fieldName;
    }
}