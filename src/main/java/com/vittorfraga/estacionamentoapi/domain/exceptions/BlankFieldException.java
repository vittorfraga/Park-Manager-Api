package com.vittorfraga.estacionamentoapi.domain.exceptions;

public class BlankFieldException extends DomainException {

    private final String fieldName;

    public BlankFieldException(String fieldName) {
        super(fieldName + " can not be empty");
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}