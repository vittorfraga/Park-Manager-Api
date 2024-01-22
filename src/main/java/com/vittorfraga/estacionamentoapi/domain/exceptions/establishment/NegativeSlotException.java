package com.vittorfraga.estacionamentoapi.domain.exceptions.establishment;

import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;

public class NegativeSlotException extends DomainException {
    private final String fieldName;

    public NegativeSlotException(String slotName) {
        super(String.format("%s should not be negative", slotName));
        this.fieldName = slotName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
