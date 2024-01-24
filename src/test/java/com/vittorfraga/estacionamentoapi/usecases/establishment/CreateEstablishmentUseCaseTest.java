package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.domain.exceptions.NullFieldException;
import com.vittorfraga.estacionamentoapi.domain.exceptions.establishment.NegativeSlotException;
import com.vittorfraga.estacionamentoapi.usecases.establishment.dtos.EstablishmentRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class CreateEstablishmentUseCaseTest {

    @Autowired
    private EstablishmentRepository repository;

    @Autowired
    private CreateEstablishmentUseCase useCase;


    @Test
    void shouldCreateEstablishment() {
        final String expectedName = "Estabelecimento Teste";
        final var expectedCnpj = "12345678901234";
        final var expectedAddress = "Endereço Teste";
        final var expectedPhone = "123456789";
        final var expectedMotorcycleSlots = 10;
        final var expectedCarSlots = 20;

        EstablishmentRequest request = new EstablishmentRequest(
                expectedName,
                expectedCnpj,
                expectedPhone,
                expectedAddress,
                expectedMotorcycleSlots,
                expectedCarSlots);

        final var result = useCase.execute(request);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());

        final var actualEstablishment = repository.findById(result.getId()).get();

        assertEquals(expectedName, actualEstablishment.getName());
        assertEquals(expectedCnpj, actualEstablishment.getCnpj());
        assertEquals(expectedAddress, actualEstablishment.getAddress());
        assertEquals(expectedPhone, actualEstablishment.getPhone());
        assertEquals(expectedMotorcycleSlots, actualEstablishment.getMotorcycleSlots());
        assertEquals(expectedCarSlots, actualEstablishment.getCarSlots());
    }


    @Test
    void givenAnInvalidNullName_whenCreateEstablishment_thenShouldThrowsException() {
        final String expectedName = null;
        final var expectedCnpj = "12345678901234";
        final var expectedAddress = "Endereço Teste";
        final var expectedPhone = "123456789";
        final var expectedMotorcycleSlots = 10;
        final var expectedCarSlots = 20;

        EstablishmentRequest request = new EstablishmentRequest(
                expectedName,
                expectedCnpj,
                expectedPhone,
                expectedAddress,
                expectedMotorcycleSlots,
                expectedCarSlots);

        Assertions.assertThrows(NullFieldException.class, () -> {
            useCase.execute(request);
        });
    }

    @Test
    void givenAnInvalidNegativeMotorcycleSlots_whenCreateEstablishment_thenShouldThrowsException() {
        final String expectedName = "Estabelecimento Teste";
        final var expectedCnpj = "12345678901234";
        final var expectedAddress = "Endereço Teste";
        final var expectedPhone = "123456789";
        final var expectedMotorcycleSlots = -10;
        final var expectedCarSlots = 20;
        final var expectedErrorMessage = "motorcycleSlots should not be negative";


        EstablishmentRequest request = new EstablishmentRequest(
                expectedName,
                expectedCnpj,
                expectedPhone,
                expectedAddress,
                expectedMotorcycleSlots,
                expectedCarSlots);

        final var expection = Assertions.assertThrows(NegativeSlotException.class, () -> {
            useCase.execute(request);
        });

        assertEquals(expectedErrorMessage, expection.getMessage());
    }
}
