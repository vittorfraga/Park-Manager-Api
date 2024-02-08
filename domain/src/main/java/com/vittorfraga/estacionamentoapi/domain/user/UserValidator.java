package com.vittorfraga.estacionamentoapi.domain.user;

import com.vittorfraga.estacionamentoapi.domain.Validator;
import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private static final int USERNAME_MIN_LENGTH = 6;
    private static final int USERNAME_MAX_LENGTH = 18;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 100;
    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 30;

    public static void validateUserFields(User user) {
        Validator.validateNotNullOrBlank(user.getUsername(), "username");
        Validator.validateNotNullOrBlank(user.getPassword(), "password");
        Validator.validateNotNullOrBlank(user.getName(), "name");
        Validator.validateNotNullOrBlank(user.getEmail(), "email");

        validateEmail(user.getEmail());

        validateLength(user.getUsername(), "username", USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH);
        validateLength(user.getPassword(), "password", PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
        validateLength(user.getName(), "name", NAME_MIN_LENGTH, NAME_MAX_LENGTH);
    }

    private static void validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new DomainException("invalid email format");
        }
    }

    private static void validateLength(String value, String fieldName, int minLength, int maxLength) {
        if (value.length() < minLength || value.length() > maxLength) {
            throw new DomainException(fieldName, String.format("must be between %d and %d characters", minLength, maxLength));
        }
    }
}
