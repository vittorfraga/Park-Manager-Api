package com.vittorfraga.estacionamentoapi.domain.exceptions;

public class InvalidLengthException extends DomainException {
    private final String fieldName;

    public InvalidLengthException(String fieldName, int minLength, int maxLength) {
        super(String.format("%s must be between %d and %d characters", fieldName, minLength, maxLength));
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}

