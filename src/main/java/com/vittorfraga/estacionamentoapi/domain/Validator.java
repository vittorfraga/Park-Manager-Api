package com.vittorfraga.estacionamentoapi.domain;

import com.vittorfraga.estacionamentoapi.domain.exceptions.BlankFieldException;
import com.vittorfraga.estacionamentoapi.domain.exceptions.InvalidLengthException;
import com.vittorfraga.estacionamentoapi.domain.exceptions.NullFieldException;

import java.util.Objects;

public class Validator {
    public static void validateNotNullOrBlank(String fieldValue, String fieldName) {
        validateNotNull(fieldValue, fieldName);
        if (fieldValue.trim().isEmpty()) {
            throw new BlankFieldException(fieldName);
        }
    }

    public static void validateNotNull(Object fieldValue, String fieldName) {
        if (Objects.isNull(fieldValue)) {
            throw new NullFieldException(fieldName);
        }
    }

    public static void validateLength(String fieldValue, String fieldName, int minLength, int maxLength) {
        if (fieldValue.length() < minLength || fieldValue.length() > maxLength) {
            throw new InvalidLengthException(fieldName, minLength, maxLength);
        }
    }
}
