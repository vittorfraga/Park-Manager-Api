package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.ResourceNotFoundException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetVehicleByIdUseCaseTest {

    @InjectMocks
    private GetVehicleByIdUseCase useCase;

    @Mock
    private VehicleRepository repository;


    @Test
    void givenAnValidId_whenCallsGetVehicleById_thenShouldReturnAVehicle() {

        final var vehicleSaved = VehicleTestHelper.createAndSaveVehicle(repository);

        final var expectedId = vehicleSaved.getId();

        when(repository.findById(expectedId)).thenReturn(java.util.Optional.of(vehicleSaved));

        final var actualVehicle = useCase.execute(expectedId);

        assertEquals(expectedId, actualVehicle.getId());
        assertEquals(vehicleSaved.getBrand(), actualVehicle.getBrand());
        assertEquals(vehicleSaved.getModel(), actualVehicle.getModel());
        assertEquals(vehicleSaved.getLicensePlate(), actualVehicle.getLicensePlate());
        assertEquals(vehicleSaved.getColor(), actualVehicle.getColor());
        assertEquals(vehicleSaved.getType(), actualVehicle.getType());
    }

    @Test
    void givenAInvalidId_whenCallsGetVehicleById_thenShouldThrowsException() {
        final var expectedId = 123L;
        final var expectedErrorMessage = "Could not find vehicle with Id " + expectedId;

        when(repository.findById(eq(expectedId))).thenReturn(Optional.empty());


        final var actualException = Assertions.assertThrows(ResourceNotFoundException.class, () -> useCase.execute(expectedId));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        Assertions.assertEquals(actualException.getClass(), ResourceNotFoundException.class);

    }
}