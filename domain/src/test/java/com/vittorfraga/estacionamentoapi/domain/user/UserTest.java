package com.vittorfraga.estacionamentoapi.domain.user;

import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    @Test
    void givenValidParams_whenCallNewUser_thenShouldCreateNewUser() {

        final var expectedUsername = "usertest";
        final var expectedPassword = "123456";
        final var expectedName = "User Test";
        final var expectedEmail = "user-test@email.com";

        final var actualUser = User.builder(expectedUsername, expectedPassword, expectedName, expectedEmail);

        Assertions.assertNotNull(actualUser);
        Assertions.assertNotNull(actualUser.getId());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedPassword, actualUser.getPassword());
        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedEmail, actualUser.getEmail());
    }

    @Test
    void givenNullUsername_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "username can not be null";
        final var expectedFieldName = "username";

        UserBuilder builder = new UserBuilder().withUsername(null);

        final var exception = Assertions.assertThrows(DomainException.class, builder::build);

        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenEmptyUsername_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "username can not be empty";
        final var expectedFieldName = "username";

        UserBuilder builder = new UserBuilder().withUsername("");

        final var exception = Assertions.assertThrows(DomainException.class, builder::build);

        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullPassword_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "password can not be null";
        final var expectedFieldName = "password";

        UserBuilder builder = new UserBuilder().withPassword(null);

        final var exception = Assertions.assertThrows(DomainException.class, builder::build);

        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenEmptyPassword_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "password can not be empty";
        final var expectedFieldName = "password";

        UserBuilder builder = new UserBuilder().withPassword("");

        final var exception = Assertions.assertThrows(DomainException.class, builder::build);

        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenAnInvalidEmail_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "invalid email format";

        UserBuilder builder = new UserBuilder().withEmail("invalid-email");

        final var exception = Assertions.assertThrows(DomainException.class, builder::build);

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenInvalidShortUsernameLength_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "username must be between 6 and 18 characters";
        final var expectedFieldName = "username";

        UserBuilder builder = new UserBuilder().withUsername("us");
        System.out.println(builder);

        final var exception = Assertions.assertThrows(DomainException.class, builder::build);

        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenInvalidLongUsernameLength_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "username must be between 6 and 18 characters";
        final var expectedFieldName = "username";

        UserBuilder builder = new UserBuilder().withUsername("usertestusertestusertestusertestusertestusertestusertestusertestusertestusertestusertestusertest");

        final var exception = Assertions.assertThrows(DomainException.class, builder::build);

        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenInvalidShortPasswordLength_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "password must be between 6 and 100 characters";
        final var expectedFieldName = "password";

        UserBuilder builder = new UserBuilder().withPassword("12345");

        final var exception = Assertions.assertThrows(DomainException.class, builder::build);

        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenInvalidLongPasswordLength_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "password must be between 6 and 100 characters";
        final var expectedFieldName = "password";
        final var longPassword = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678903456789012345678901234567890123456";

        UserBuilder builder = new UserBuilder().withPassword(longPassword);

        final var exception = Assertions.assertThrows(DomainException.class, builder::build);

        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedFieldName, exception.getFieldName());

    }

    @Test
    void givenInvalidShortNameLength_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "name must be between 3 and 30 characters";
        final var expectedFieldName = "name";

        UserBuilder builder = new UserBuilder().withName("Us");

        final var exception = Assertions.assertThrows(DomainException.class, builder::build);

        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenInvalidLongNameLength_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "name must be between 3 and 30 characters";
        final var expectedFieldName = "name";
        final var longName = "fjsodifjsdfjsfijsdidfdsfdsfsdffjsdofijsdofijsdoifjsoidfjosidjfi";

        UserBuilder builder = new UserBuilder().withName(longName);

        final var exception = Assertions.assertThrows(DomainException.class, builder::build);

        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenValidParams_whenCallUpdate_thenShouldReturnUserUpdated() {
        final var expectedUpdatedUsername = "usertestupdated";
        final var expectedUpdatedPassword = "123456updated";
        final var expectedUpdatedName = "User Test Updated";
        final var expectedUpdatedEmail = "email_updated10@email.com.br";

        final var actualVehicle = new UserBuilder().build();


        actualVehicle.update(expectedUpdatedUsername, expectedUpdatedPassword, expectedUpdatedName, expectedUpdatedEmail);


        assertEquals(expectedUpdatedUsername, actualVehicle.getUsername());
        assertEquals(expectedUpdatedPassword, actualVehicle.getPassword());
        assertEquals(expectedUpdatedName, actualVehicle.getName());
        assertEquals(expectedUpdatedEmail, actualVehicle.getEmail());
    }

    @Test
    void givenAnIvalidNullUsername_whenCallsUpdate_thenShouldKeepTheOldUsername() {
        final String expectedUpdatedUsername = null;
        final var expectedUpdatedPassword = "123456updated";
        final var expectedUpdatedName = "User Test Updated";
        final var expectedUpdatedEmail = "email_updated10@email.com.br";

        final var actualVehicle = new UserBuilder().build();


        actualVehicle.update(expectedUpdatedUsername, expectedUpdatedPassword, expectedUpdatedName, expectedUpdatedEmail);

        assertNotNull(actualVehicle.getUsername());
    }
}