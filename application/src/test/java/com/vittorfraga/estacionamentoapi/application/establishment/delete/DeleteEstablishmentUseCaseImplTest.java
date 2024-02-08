package com.vittorfraga.estacionamentoapi.application.establishment.delete;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteEstablishmentUseCaseImplTest {
    @InjectMocks
    private DeleteEstablishmentUseCaseImpl useCase;

    @Mock
    private EstablishmentGateway gateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(gateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteEstablishment_shouldBeOk() {
        final var anEstablishment = Establishment.builder("Establishment", "33333333333333", "someAddress", "51987456321", 10, 10);
        final var expectedId = anEstablishment.getId();

        when(gateway.findById(eq(expectedId))).thenReturn(Optional.of(anEstablishment));
        
        doNothing().when(gateway).delete(eq(expectedId));

        final var Input = new DeleteEstablishmentInput(expectedId);

        Assertions.assertDoesNotThrow(() -> useCase.execute(Input));

        Mockito.verify(gateway, times(1)).delete(eq(expectedId));
    }

    @Test
    public void givenAnInvalidId_whenCallsDeleteEstablishment_shouldThrowException() {
        final var expectedId = "1234567890";

        when(gateway.findById(eq(expectedId))).thenReturn(Optional.empty());

        final var Input = new DeleteEstablishmentInput(expectedId);

        Assertions.assertThrows(DomainException.class, () -> useCase.execute(Input));

        Mockito.verify(gateway, times(0)).delete(eq(expectedId));
    }
}