package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.ResourceNotFoundException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.dto.UpdateVehicleRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateVehicleUseCaseTest {
    @InjectMocks
    private UpdateVehicleUseCase useCase;

    @Mock
    private VehicleRepository repository;

    @Test
    void givenAValidCommand_whenCallsUpdateVehicle_thenShouldReturnAVehicleUpdated() {
        final var vehicleSaved = VehicleTestHelper.createAndSaveVehicle(repository);
        final var expectedId = vehicleSaved.getId();

        final var expectedNewBrand = "Brand Updated";
        final var expectedNewModel = "Model Updated";
        final var expectedNewLicensePlate = "ABC1234";
        final var expectedNewColor = "Color Updated";
        final var expectedNewType = "CAR";

        final var vehicleToUpdate = new UpdateVehicleRequest(expectedId, expectedNewBrand, expectedNewModel, expectedNewLicensePlate, expectedNewColor, expectedNewType);

        when(repository.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(vehicleSaved));

        when(repository.save(Mockito.any())).thenReturn(vehicleSaved);

        final var actualVehicle = useCase.execute(vehicleToUpdate);

        assertEquals(expectedId, actualVehicle.getId());
        assertEquals(expectedNewBrand, actualVehicle.getBrand());
        assertEquals(expectedNewModel, actualVehicle.getModel());
        assertEquals(expectedNewLicensePlate, actualVehicle.getLicensePlate());
        assertEquals(expectedNewColor, actualVehicle.getColor());

    }

    @Test
    void givenAnInvalidId_whenCallsUpdateVehicle_thenShouldThrowsException() {
        final var expectedId = 123L;
        final var expectedErrorMessage = "Could not find vehicle with Id " + expectedId;

        final var expectedNewBrand = "Brand Updated";
        final var expectedNewModel = "Model Updated";
        final var expectedNewLicensePlate = "ABC1234";
        final var expectedNewColor = "Color Updated";
        final var expectedNewType = "CAR";

        final var vehicleToUpdate = new UpdateVehicleRequest(expectedId, expectedNewBrand, expectedNewModel, expectedNewLicensePlate, expectedNewColor, expectedNewType);

        when(repository.findById(Mockito.eq(expectedId))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(ResourceNotFoundException.class, () -> useCase.execute(vehicleToUpdate));

        assertEquals(expectedErrorMessage, actualException.getMessage());
        assertEquals(actualException.getClass(), ResourceNotFoundException.class);

    }

    @Test
    void givenAnInvalidNullModel_whenCallsUpdateVehicle_thenShouldThrowsException() {
        final var vehicleSaved = VehicleTestHelper.createAndSaveVehicle(repository);
        final var expectedId = vehicleSaved.getId();

        final var expectedNewBrand = "Brand Updated";
        final String expectedNewModel = null;
        final var expectedNewLicensePlate = "ABC1234";
        final var expectedNewColor = "Color Updated";
        final var expectedNewType = "MOTORCYCLE";

        final var vehicleToUpdate = new UpdateVehicleRequest(expectedId, expectedNewBrand, expectedNewModel, expectedNewLicensePlate, expectedNewColor, expectedNewType);

        final var actualException = Assertions.assertThrows(NullPointerException.class, () -> useCase.execute(vehicleToUpdate));

        assertEquals("model should not be null", actualException.getMessage());
        assertEquals(actualException.getClass(), NullPointerException.class);

    }
}