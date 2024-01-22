package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.domain.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
class GetEstablishmentByIdUseCaseTest {
    @Autowired
    private EstablishmentRepository repository;

    @Autowired
    private GetEstablishmentByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        this.repository.deleteAll();
    }

    @Test
    void givenAValidId_whenCallsGetEstablishmentByIdUseCase_thenShouldReturnEstablishment() {
        final String expectedName = "Estabelecimento Teste";
        final var expectedCnpj = "12345678901234";
        final var expectedAddress = "Endereço Teste";
        final var expectedPhone = "123456789";
        final var expectedMotorcycleSlots = 10;
        final var expectedCarSlots = 20;

        final var anEstablishment = new Establishment(
                expectedName,
                expectedCnpj,
                expectedAddress,
                expectedPhone,
                expectedMotorcycleSlots,
                expectedCarSlots);

        final var createdEstablishment = this.repository.save(anEstablishment);

        final var actualEstablishment = useCase.execute(createdEstablishment.getId());

        assertNotNull(actualEstablishment);
        assertNotNull(actualEstablishment.getId());

        assertEquals(expectedName, actualEstablishment.getName());
        assertEquals(expectedCnpj, actualEstablishment.getCnpj());
        assertEquals(expectedAddress, actualEstablishment.getAddress());
        assertEquals(expectedPhone, actualEstablishment.getPhone());
        assertEquals(expectedMotorcycleSlots, actualEstablishment.getMotorcycleSlots());
        assertEquals(expectedCarSlots, actualEstablishment.getCarSlots());

    }

    @Test
    void givenAnInvalidId_whenCallsGetEstablishmentByIdUseCase_thenShouldThrowException() {
        final var expectedId = 10L;
        final var expectedErrorMessage = String.format("Could not find Establishment with Id %d", expectedId);

        final var actualException = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(expectedId)
        );

        assertEquals(expectedErrorMessage, actualException.getMessage());
    }

}