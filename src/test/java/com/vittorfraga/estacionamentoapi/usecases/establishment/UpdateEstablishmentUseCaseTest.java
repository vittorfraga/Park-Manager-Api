package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.domain.exceptions.ResourceNotFoundException;
import com.vittorfraga.estacionamentoapi.usecases.establishment.dtos.UpdateEstablishmentRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateEstablishmentUseCaseTest {

    @Autowired
    private EstablishmentRepository repository;

    @Autowired
    private UpdateEstablishmentUseCase useCase;

    @Test
    void givenAValidCommand_whenCallsUpdateEstablishmentUseCase_thenShouldUpdateEstablishment() {
        final var expectedName = "Estabelecimento Teste";
        final var expectedCnpj = "12345678901234";
        final var expectedAddress = "Endereço Teste";
        final var expectedPhone = "123456789";
        final var expectedMotorcycleSlots = 10;
        final var expectedCarSlots = 20;

        //create an instance of Establishment
        final var anEstablishment = new Establishment(
                "name",
                "12345678901233",
                "address",
                "expectedPhone",
                5,
                3);

        final var createdEstablishment = this.repository.save(anEstablishment);


        final var input = new UpdateEstablishmentRequest(
                createdEstablishment.getId(),
                expectedName,
                expectedCnpj,
                expectedAddress,
                expectedPhone,
                expectedMotorcycleSlots,
                expectedCarSlots);


        final var actualEstablishment = useCase.execute(input);


        Assertions.assertNotNull(actualEstablishment);
        Assertions.assertNotNull(actualEstablishment.getId());
        Assertions.assertEquals(expectedName, actualEstablishment.getName());
        Assertions.assertEquals(expectedCnpj, actualEstablishment.getCnpj());
        Assertions.assertEquals(expectedAddress, actualEstablishment.getAddress());
        Assertions.assertEquals(expectedPhone, actualEstablishment.getPhone());
        Assertions.assertEquals(expectedMotorcycleSlots, actualEstablishment.getMotorcycleSlots());
        Assertions.assertEquals(expectedCarSlots, actualEstablishment.getCarSlots());
    }

    @Test
    void givenAnInvalidId_whenCallsUpdateEstablishmentUseCase_thenShouldThrowException() {
        final var expectedId = 10L;
        final var expectedName = "Estabelecimento Teste";
        final var expectedCnpj = "12345678901234";
        final var expectedAddress = "Endereço Teste";
        final var expectedPhone = "123456789";
        final var expectedMotorcycleSlots = 10;
        final var expectedCarSlots = 20;
        final var expectedErrorMessage = String.format("Could not find Establishment with Id %d", expectedId);

        final var input = new UpdateEstablishmentRequest(
                expectedId,
                expectedName,
                expectedCnpj,
                expectedAddress,
                expectedPhone,
                expectedMotorcycleSlots,
                expectedCarSlots);

        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> useCase.execute(input));

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(exception.getClass(), ResourceNotFoundException.class);
    }

    @Test
    void givenAnInvalidNullAddress_whenCallsUpdateEstablishmentUseCase_thenShouldThrowException() {
        final var expectedName = "Estabelecimento Teste";
        final var expectedCnpj = "12345678901234";
        final String expectedAddress = null;
        final var expectedPhone = "123456789";
        final var expectedMotorcycleSlots = 10;
        final var expectedCarSlots = 20;
        final var expectedErrorMessage = "address should not be null";

        final var anEstablishment = new Establishment(
                "name",
                "12345678901233",
                "address",
                "expectedPhone",
                5,
                3);


        final var createdEstablishment = this.repository.save(anEstablishment);

        final var input = new UpdateEstablishmentRequest(
                createdEstablishment.getId(),
                expectedName,
                expectedCnpj,
                expectedAddress,
                expectedPhone,
                expectedMotorcycleSlots,
                expectedCarSlots);


        final var exception = Assertions.assertThrows(NullPointerException.class, () -> useCase.execute(input));


        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(exception.getClass(), NullPointerException.class);
    }

}
