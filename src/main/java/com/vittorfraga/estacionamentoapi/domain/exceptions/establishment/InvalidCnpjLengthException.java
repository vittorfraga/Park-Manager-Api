package com.vittorfraga.estacionamentoapi.domain.exceptions.establishment;

import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;

public class InvalidCnpjLengthException extends DomainException {

    private final String fieldName;

    public InvalidCnpjLengthException(int expectedLength) {
        super(String.format("'cnpj' must be %d characters", expectedLength));
        this.fieldName = "cnpj";
    }

    public String getFieldName() {
        return fieldName;
    }
}
