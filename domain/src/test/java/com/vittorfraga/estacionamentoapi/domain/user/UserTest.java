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

        assertNotNull(actualUser);
        assertNotNull(actualUser.getId());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedPassword, actualUser.getPassword());
        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedEmail, actualUser.getEmail());
    }

    @Test
    void givenNullUsername_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "username can not be null";
        final var expectedErrorCount = 1;

        UserBuilder builder = new UserBuilder().withUsername(null);

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenEmptyUsername_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "username can not be empty";
        final var expectedErrorCount = 1;

        UserBuilder builder = new UserBuilder().withUsername("");

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenNullPassword_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "password can not be null";
        final var expectedErrorCount = 1;

        UserBuilder builder = new UserBuilder().withPassword(null);

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenEmptyPassword_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "password can not be empty";
        final var expectedErrorCount = 1;

        UserBuilder builder = new UserBuilder().withPassword("");

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenAnInvalidEmail_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "invalid email format";
        final var expectedErrorCount = 1;


        UserBuilder builder = new UserBuilder().withEmail("invalid-email");

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidShortUsernameLength_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "username must be between 6 and 18 characters";
        final var expectedErrorCount = 1;

        UserBuilder builder = new UserBuilder().withUsername("us");
        System.out.println(builder);

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidLongUsernameLength_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "username must be between 6 and 18 characters";
        final var expectedErrorCount = 1;

        UserBuilder builder = new UserBuilder().withUsername("usertestusertestusertestusertestusertestusertestusertestusertestusertestusertestusertestusertest");

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidShortPasswordLength_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "password must be between 6 and 100 characters";
        final var expectedErrorCount = 1;

        UserBuilder builder = new UserBuilder().withPassword("12345");

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidLongPasswordLength_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "password must be between 6 and 100 characters";
        final var expectedErrorCount = 1;
        final var longPassword = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678903456789012345678901234567890123456";

        UserBuilder builder = new UserBuilder().withPassword(longPassword);

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());

    }

    @Test
    void givenInvalidShortNameLength_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "name must be between 3 and 30 characters";
        final var expectedErrorCount = 1;

        UserBuilder builder = new UserBuilder().withName("Us");

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidLongNameLength_whenCallNewUser_thenShouldThrowException() {
        final var expectedErrorMessage = "name must be between 3 and 30 characters";
        final var expectedErrorCount = 1;
        final var longName = "fjsodifjsdfjsfijsdidfdsfdsfsdffjsdofijsdofijsdoifjsoidfjosidjfi";

        UserBuilder builder = new UserBuilder().withName(longName);

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
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