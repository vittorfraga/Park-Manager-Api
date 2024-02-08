package com.vittorfraga.estacionamentoapi.domain.establishment;

import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EstablishmentTest {
    @Test
    void givenValidParams_whenCallNewEstablishment_thenShouldCreateNewEstablishment() {

        final var expectedName = "Establishment";
        final var expectedCNPJ = "12345678901234";
        final var expectedAddress = ("Rua ABC 123, CidadeX, PR");
        final var expectedPhone = "+55 123456789";
        final var expectedMotorcycleSlots = 20;
        final var expectedCarSlots = 30;


        final var actualEstablishment = Establishment.builder(expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorcycleSlots, expectedCarSlots);
        System.out.println(actualEstablishment.getId());
        Assertions.assertNotNull(actualEstablishment);
        Assertions.assertNotNull(actualEstablishment.getId());
        Assertions.assertEquals(expectedName, actualEstablishment.getName());
        Assertions.assertEquals(expectedCNPJ, actualEstablishment.getCnpj());
        Assertions.assertEquals(expectedAddress, actualEstablishment.getAddress());
        Assertions.assertEquals(expectedPhone, actualEstablishment.getPhone());
        Assertions.assertEquals(expectedMotorcycleSlots, actualEstablishment.getMotorcycleSlots());
        Assertions.assertEquals(expectedCarSlots, actualEstablishment.getCarSlots());
    }


    @Test
    void givenNullName_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedErrorMessage = "name can not be null";
        var expectedFieldName = "name";


        EstablishmentBuilder builder = new EstablishmentBuilder().withName(null);

        var exception = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());

    }

    @Test
    void givenEmptyName_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "name";
        var expectedErrorMessage = "name can not be empty";

        EstablishmentBuilder builder = new EstablishmentBuilder().withName("");

        var exception = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullCNPJ_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "cnpj";
        var expectedErrorMessage = "cnpj can not be null";

        EstablishmentBuilder builder = new EstablishmentBuilder().withCnpj(null);

        var exception = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenEmptyCNPJ_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "cnpj";
        var expectedErrorMessage = "cnpj can not be empty";

        EstablishmentBuilder builder = new EstablishmentBuilder().withCnpj("");

        var exception = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullAddress_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "address";
        var expectedErrorMessage = "address can not be null";

        EstablishmentBuilder builder = new EstablishmentBuilder().withAddress(null);

        var exception = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullPhone_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "phone";
        var expectedErrorMessage = "phone can not be null";

        EstablishmentBuilder builder = new EstablishmentBuilder().withPhone(null);

        var exception = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullMotorCycleSlots_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "motorcycleSlots";
        var expectedErrorMessage = "motorcycleSlots can not be null";

        EstablishmentBuilder builder = new EstablishmentBuilder().withMotorCycleSlots(null);

        var exception = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNegativeMotorCycleSlots_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "motorcycleSlots";
        var expectedErrorMessage = "motorcycleSlots should not be negative";

        EstablishmentBuilder builder = new EstablishmentBuilder().withMotorCycleSlots(-1);

        var exception = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullCarSlots_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "carSlots";
        var expectedErrorMessage = "carSlots can not be null";

        EstablishmentBuilder builder = new EstablishmentBuilder().withCarSlots(null);

        var exception = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNegativeCarSlots_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "carSlots";
        var expectedErrorMessage = "carSlots should not be negative";

        EstablishmentBuilder builder = new EstablishmentBuilder().withCarSlots(-1);

        var exception = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenValidParams_whenCallUpdate_thenShouldUpdateEstablishment() {
        // Given
        final var expectedName = "Establishment";
        final var expectedCNPJ = "12345678901234";
        final var expectedAddress = ("Rua ABC 123, CidadeX, PR");
        final var expectedPhone = "+55 123456789";
        final var expectedMotorcycleSlots = 20;
        final var expectedCarSlots = 30;

        final var establishment = Establishment.builder(expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorcycleSlots, expectedCarSlots);

        final var expectedUpdatedName = "Updated Establishment";
        final var expectedUpdatedCNPJ = "12345678901234";
        final var expectedUpdatedAddress = ("Rua ABC 123, CidadeX, RS");
        final var expectedUpdatedPhone = "+55 123456789";
        final var expectedUpdatedMotorcycleSlots = 30;
        final var expectedUpdatedCarSlots = 50;

        final var actualEstablishment = establishment.update(expectedUpdatedName, expectedUpdatedCNPJ, expectedUpdatedAddress, expectedUpdatedPhone, expectedUpdatedMotorcycleSlots, expectedUpdatedCarSlots);

        Assertions.assertNotNull(actualEstablishment);
        Assertions.assertNotNull(actualEstablishment.getId());
        Assertions.assertEquals(expectedUpdatedName, actualEstablishment.getName());
        Assertions.assertEquals(expectedUpdatedCNPJ, actualEstablishment.getCnpj());
        Assertions.assertEquals(expectedUpdatedAddress, actualEstablishment.getAddress());
        Assertions.assertEquals(expectedUpdatedPhone, actualEstablishment.getPhone());
        Assertions.assertEquals(expectedUpdatedMotorcycleSlots, actualEstablishment.getMotorcycleSlots());
        Assertions.assertEquals(expectedUpdatedCarSlots, actualEstablishment.getCarSlots());

    }
    
}

