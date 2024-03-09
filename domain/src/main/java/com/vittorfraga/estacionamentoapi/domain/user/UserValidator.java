package com.vittorfraga.estacionamentoapi.domain.user;

import com.vittorfraga.estacionamentoapi.domain.validation.Error;
import com.vittorfraga.estacionamentoapi.domain.validation.ValidationHandler;
import com.vittorfraga.estacionamentoapi.domain.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator extends Validator {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private static final int USERNAME_MIN_LENGTH = 6;
    private static final int USERNAME_MAX_LENGTH = 18;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 100;
    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 30;


    private final User user;

    protected UserValidator(final User anUser, final ValidationHandler aHandler) {
        super(aHandler);
        this.user = anUser;
    }

    @Override
    public void validate() {
        validateNotNullOrBlank(this.user.getUsername(), "username");
        validateNotNullOrBlank(this.user.getPassword(), "password");
        validateNotNullOrBlank(this.user.getName(), "name");
        validateNotNullOrBlank(this.user.getEmail(), "email");

        validateEmail(this.user.getEmail());

        validateLength(this.user.getUsername(), "username", USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH);
        validateLength(this.user.getPassword(), "password", PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
        validateLength(this.user.getName(), "name", NAME_MIN_LENGTH, NAME_MAX_LENGTH);
    }


    private void validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            this.validationHandler().append(new Error(("invalid email format")));
        }
    }


}
