package com.vittorfraga.estacionamentoapi.domain.validation;

import java.util.Objects;

public abstract class Validator {

    private final ValidationHandler handler;

    protected Validator(final ValidationHandler aHandler) {
        this.handler = aHandler;
    }

    public abstract void validate();

    protected ValidationHandler validationHandler() {
        return this.handler;
    }


    public void validateNotNullOrBlank(String fieldValue, String fieldName) {
        validateNotNull(fieldValue, fieldName);
        if (fieldValue.trim().isEmpty()) {
            this.validationHandler().append(new Error(fieldName + " can not be empty"));
        }
    }


    public void validateNotNull(Object fieldValue, String fieldName) {
        if (Objects.isNull(fieldValue)) {
            this.validationHandler().append(new Error(fieldName + " can not be null"));
        }
    }

    public void validateLength(String fieldValue, String fieldName, int minLength, int maxLength) {
        if (fieldValue.length() < minLength || fieldValue.length() > maxLength) {
            this.validationHandler().append(new Error(String.format("%s must be between %d and %d characters", fieldName, minLength, maxLength)));
        }
    }
}
