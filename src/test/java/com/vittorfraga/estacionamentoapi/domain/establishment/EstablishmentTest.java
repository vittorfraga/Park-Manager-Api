package com.vittorfraga.estacionamentoapi.domain.establishment;


//Teste de criação Bem-Sucedida de um Establishment
//Teste de criação de um Establishment com a propriedade 'name' null
//Teste de criação de um Establishment com a propriedade 'name' empty
//Teste de criação de um Establishment com a propriedade 'CNPJ' null
//Teste de criação de um Establishment com a propriedade 'CNPJ' empty
//Teste de criação de um Establishment com a propriedade 'address' null
//Teste de criação de um Establishment com 'motorcycleSpot' null
//Teste de criação de um Establishment com 'motorcycleSpot' negativo
//Teste de criação de um Establishment com 'carSpacesSpot' negativo
//Teste de criação de um Establishment com 'carSpacesSpot' null
//Teste de update de um Establishment com sucesso
//Teste de update de um Establishment com com 'carSpaces' negativo
//Teste nome inválido com menos de 3 caracteres
//Teste nome inválido com mais de 255 caracteres
//Teste telefone inválido com mais de 30 caracteres
//Teste CNPJ inválido com mais de 14 caracteres


import com.vittorfraga.estacionamentoapi.domain.exceptions.BlankFieldException;
import com.vittorfraga.estacionamentoapi.domain.exceptions.InvalidLengthException;
import com.vittorfraga.estacionamentoapi.domain.exceptions.NullFieldException;
import com.vittorfraga.estacionamentoapi.domain.exceptions.establishment.InvalidCnpjLengthException;
import com.vittorfraga.estacionamentoapi.domain.exceptions.establishment.NegativeSlotException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EstablishmentTest {
    @Test
    void givenValidParams_whenCallNewEstablishment_thenShouldCreateNewEstablishment() {
        // Given
        final var expectedName = "Establishment";
        final var expectedCNPJ = "12345678901234";
        final var expectedAddress = ("Rua ABC 123, CidadeX, PR");
        final var expectedPhone = "+55 123456789";
        final var expectedMotorCycleSlots = 20;
        final var expectedCarSlots = 30;

        // When
        final var actualEstablishment = new Establishment(expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorCycleSlots, expectedCarSlots);

        // Then
        Assertions.assertNotNull(actualEstablishment);
        Assertions.assertEquals(expectedName, actualEstablishment.getName());
        Assertions.assertEquals(expectedCNPJ, actualEstablishment.getCnpj());
        Assertions.assertEquals(expectedAddress, actualEstablishment.getAddress());
        Assertions.assertEquals(expectedPhone, actualEstablishment.getPhone());
        Assertions.assertEquals(expectedMotorCycleSlots, actualEstablishment.getMotorcycleSlots());
        Assertions.assertEquals(expectedCarSlots, actualEstablishment.getCarSlots());

    }

    @Test
    void givenNullName_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedErrorMessage = "name can not be null";
        var expectedFieldName = "name";

        NullFieldException exception = Assertions.assertThrows(
                NullFieldException.class,
                () -> new EstablishmentBuilder().withName(null).build(),
                "Expected exception to be thrown"
        );


        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }


    @Test
    void givenEmptyName_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "name";
        var expectedErrorMessage = "name can not be empty";

        EstablishmentBuilder builder = new EstablishmentBuilder().withName("");

        var exception = Assertions.assertThrows(BlankFieldException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullCNPJ_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "cnpj";
        var expectedErrorMessage = "cnpj can not be null";

        EstablishmentBuilder builder = new EstablishmentBuilder().withCnpj(null);

        var exception = Assertions.assertThrows(NullFieldException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenEmptyCNPJ_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "cnpj";
        var expectedErrorMessage = "cnpj can not be empty";

        EstablishmentBuilder builder = new EstablishmentBuilder().withCnpj("");

        var exception = Assertions.assertThrows(BlankFieldException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullAddress_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "address";
        var expectedErrorMessage = "address can not be null";

        EstablishmentBuilder builder = new EstablishmentBuilder().withAddress(null);

        var exception = Assertions.assertThrows(NullFieldException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullPhone_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "phone";
        var expectedErrorMessage = "phone can not be null";

        EstablishmentBuilder builder = new EstablishmentBuilder().withPhone(null);

        var exception = Assertions.assertThrows(NullFieldException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullMotorCycleSlots_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "motorcycleSlots";
        var expectedErrorMessage = "motorcycleSlots can not be null";

        EstablishmentBuilder builder = new EstablishmentBuilder().withMotorcycleSlots(null);

        var exception = Assertions.assertThrows(NullFieldException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNegativeMotorcycleSlots_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "motorcycleSlots";
        var expectedErrorMessage = "motorcycleSlots should not be negative";

        EstablishmentBuilder builder = new EstablishmentBuilder().withMotorcycleSlots(-1);

        var exception = Assertions.assertThrows(NegativeSlotException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullCarSlots_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "carSlots";
        var expectedErrorMessage = "carSlots can not be null";

        EstablishmentBuilder builder = new EstablishmentBuilder().withCarSlots(null);

        var exception = Assertions.assertThrows(NullFieldException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNegativeCarSlots_whenCallNewEstablishment_thenShouldThrowException() {
        var expectedFieldName = "carSlots";
        var expectedErrorMessage = "carSlots should not be negative";

        EstablishmentBuilder builder = new EstablishmentBuilder().withCarSlots(-1);

        var exception = Assertions.assertThrows(NegativeSlotException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenAnInvalidName_whenCallsNewEstablishment_shouldThrowException() {
        final var expectedFieldName = "name";
        final var expectedErrorMessage = "name must be between 3 and 255 characters";

        EstablishmentBuilder builder = new EstablishmentBuilder().withName("Es");

        var exception = Assertions.assertThrows(InvalidLengthException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(exception.getClass(), InvalidLengthException.class);
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenAnInvalidCNPJ_whenCallsNewEstablishment_shouldThrowException() {
        final var expectedFieldName = "cnpj";
        final var expectedErrorMessage = "'cnpj' must be 14 characters";

        EstablishmentBuilder builder = new EstablishmentBuilder().withCnpj("123456789012433");

        var exception = Assertions.assertThrows(InvalidCnpjLengthException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(exception.getClass(), InvalidCnpjLengthException.class);
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenAnInvalidLargeName_whenCallsNewEstablishment_shouldThrowException() {
        final var expectedFieldName = "name";
        final var expectedErrorMessage = "name must be between 3 and 255 characters";
        final var wrongName = "lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lorem tortor, interdum sed, lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lorem tortor, interdum sed,lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lorem tortor, interdum sed,lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lorem tortor, interdum sed,lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lorem tortor, interdum sed,lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lorem tortor, interdum sed, lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lorem tortor, interdum sed,lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lorem tortor, interdum sed";

        EstablishmentBuilder builder = new EstablishmentBuilder().withName(wrongName);

        var exception = Assertions.assertThrows(InvalidLengthException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(exception.getClass(), InvalidLengthException.class);
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());

    }

    @Test
    void givenAnInvalidLargePhone_whenCallsNewEstablishment_shouldThrowException() {
        final var expectedFieldName = "phone";
        final var expectedErrorMessage = "phone must be between 1 and 30 characters";
        final var wrongPhone = "+55 123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789";

        EstablishmentBuilder builder = new EstablishmentBuilder().withPhone(wrongPhone);

        var exception = Assertions.assertThrows(InvalidLengthException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(exception.getClass(), InvalidLengthException.class);
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());

    }
}