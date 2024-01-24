package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.ResourceNotFoundException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteVehicleUseCaseTest {

    @InjectMocks
    private DeleteVehicleUseCase useCase;

    @Mock
    private VehicleRepository repository;

    @Test
    void givenAValidId_whenCallsDeleteVehicleById_thenShouldDeleteAVehicle() {
        final var vehicleSaved = VehicleTestHelper.createAndSaveVehicle(repository);
        final var expectedId = vehicleSaved.getId();

        when(repository.existsById(eq(expectedId))).thenReturn(true);

        doNothing().when(repository).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId));

        verify(repository, times(1)).deleteById(eq(expectedId));
    }

    @Test
    void givenAInvalidId_whenCallsDeleteVehicleById_thenShouldThrowsException() {
        final var expectedId = 1L;
        final var expectedErrorMessage = "Could not find vehicle with Id " + expectedId;

        when(repository.existsById(eq(expectedId))).thenReturn(false);

        final var actualException = Assertions.assertThrows(ResourceNotFoundException.class, () -> useCase.execute(expectedId));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        Assertions.assertEquals(actualException.getClass(), ResourceNotFoundException.class);
    }
}