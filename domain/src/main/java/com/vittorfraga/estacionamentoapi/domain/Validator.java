package com.vittorfraga.estacionamentoapi.domain;

import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;

import java.util.Objects;

public class Validator {
    public static void validateNotNullOrBlank(String fieldValue, String fieldName) {
        validateNotNull(fieldValue, fieldName);
        if (fieldValue.trim().isEmpty()) {
            throw new DomainException(fieldName, "can not be empty");
        }
    }

    public static void validateNotNull(Object fieldValue, String fieldName) {
        if (Objects.isNull(fieldValue)) {
            throw new DomainException(fieldName, "can not be null");
        }
    }

    public static void validateLength(String fieldValue, String fieldName, int minLength, int maxLength) {
        if (fieldValue.length() < minLength || fieldValue.length() > maxLength) {
            throw new DomainException(fieldName, String.format("must be between %d and %d characters", minLength, maxLength));
        }
    }
}
