package com.vittorfraga.estacionamentoapi.application.establishment.retrieve.get;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentGateway;
import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetEstablishmentByIdUseCaseImplTest {

    @InjectMocks
    private GetEstablishmentByIdUseCaseImpl useCase;
    @Mock
    private EstablishmentGateway gateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(gateway);
    }

    @Test
    void givenAValidId_whenCallsGetEstablishmentById_shouldReturnEstablishment() {
        final var expectedName = "Establishment";
        final var expectedCNPJ = "12345678901234";
        final var expectedAddress = "Rua ABC 123, CidadeX, PR";
        final var expectedPhone = "+55123456789";
        final var expectedMotorcycleSlots = 20;
        final var expectedCarSlots = 30;

        final var anEstablishment = Establishment.builder(expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorcycleSlots, expectedCarSlots);
        final var expectedId = anEstablishment.getId();

        final var Input = new GetEstablishmentByIdInput(expectedId);

        when(gateway.findById(eq(expectedId))).thenReturn(Optional.of(anEstablishment.clone()));

        final var actualEstablishment = useCase.execute(Input);

        Assertions.assertEquals(expectedId, actualEstablishment.getId());
        Assertions.assertEquals(expectedName, actualEstablishment.getName());
        Assertions.assertEquals(expectedCNPJ, actualEstablishment.getCnpj());
        Assertions.assertEquals(expectedAddress, actualEstablishment.getAddress());
        Assertions.assertEquals(expectedPhone, actualEstablishment.getPhone());
        Assertions.assertEquals(expectedMotorcycleSlots, actualEstablishment.getMotorcycleSlots());
        Assertions.assertEquals(expectedCarSlots, actualEstablishment.getCarSlots());

    }

    @Test
    void givenAnInvalidId_whenCallsGetEstablishmentById_shouldThrowException() {
        final var expectedId = "123";
        final var expectedErrorMessage = "Establishment with ID 123 was not found";

        when(gateway.findById(eq(expectedId))).thenReturn(Optional.empty());

        final var Input = new GetEstablishmentByIdInput(expectedId);
        
        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(Input));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidId_whenCallsGetEstablishment_shouldReturnGatewayException() {
        final var expectedId = "123";
        final var expectedErrorMessage = "Gateway error";

        when(gateway.findById(eq(expectedId))).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var Input = new GetEstablishmentByIdInput(expectedId);


        final var actualException = Assertions.assertThrows(IllegalStateException.class,
                () -> useCase.execute(Input));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}