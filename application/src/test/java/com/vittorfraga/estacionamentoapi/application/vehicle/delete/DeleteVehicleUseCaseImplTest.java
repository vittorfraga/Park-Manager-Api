package com.vittorfraga.estacionamentoapi.application.vehicle.delete;

import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleGateway;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteVehicleUseCaseImplTest {

    @InjectMocks
    private DeleteVehicleUseCaseImpl useCase;

    @Mock
    private VehicleGateway gateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(gateway);
    }

    @Test
    void givenAValidId_whenCallsDeleteVehicleById_thenShouldDeleteAVehicle() {
        final var aVehicle = Vehicle.builder("brand", "model", "ABC1234", "green", VehicleType.CAR);

        final var expectedId = aVehicle.getId();

        when(gateway.existsById(eq(expectedId))).thenReturn(true);

        doNothing().when(gateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId));

        verify(gateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    void givenAInvalidId_whenCallsDeleteVehicleById_thenShouldThrowsException() {
        final var expectedId = "dfkjsdhfhdfjdhf";
        final var expectedErrorMessage = "Vehicle with ID " + expectedId + " was not found"; // Adicionado espaço após o ID

        when(gateway.existsById(eq(expectedId))).thenReturn(false);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(expectedId));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage()); //
        Assertions.assertEquals(actualException.getClass(), DomainException.class);
    }


}