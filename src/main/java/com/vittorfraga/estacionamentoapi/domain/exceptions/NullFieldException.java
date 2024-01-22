package com.vittorfraga.estacionamentoapi.domain.exceptions;

public class NullFieldException extends DomainException {
    private final String fieldName;

    public NullFieldException(String fieldName) {
        super(fieldName + " can not be null");
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}

