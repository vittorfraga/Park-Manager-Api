package com.vittorfraga.estacionamentoapi.usecases.vehicle;


import com.vittorfraga.estacionamentoapi.domain.exceptions.BlankFieldException;
import com.vittorfraga.estacionamentoapi.domain.exceptions.NullFieldException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.dto.VehicleRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateVehicleUseCaseTest {

    @InjectMocks
    private CreateVehicleUseCase useCase;

    @Mock
    private VehicleRepository repository;

    @Test
    void givenAValidCommand_whenCallsCreateVehicle_shouldReturnNewVehicleWithAnId() {
        final var expectedBrand = "Brand";
        final var expectedModel = "Model";
        final var expectedLicensePlate = "31A2B3";
        final var expectedColor = "verde";
        final var expectedType = "CAR";

        final var vehicleRequest = new VehicleRequest(expectedBrand, expectedModel, expectedLicensePlate, expectedColor, expectedType);

        when(repository.save(any(Vehicle.class))).thenAnswer(invocation -> {
            Vehicle vehicleToSave = invocation.getArgument(0);
            return new Vehicle(1L, vehicleToSave.getBrand(), vehicleToSave.getModel(), vehicleToSave.getLicensePlate(), vehicleToSave.getColor(), vehicleToSave.getType());
        });

        Vehicle savedVehicle = useCase.execute(vehicleRequest);

        assertNotNull(savedVehicle.getId());
        assertEquals(expectedBrand, savedVehicle.getBrand());
        assertEquals(expectedModel, savedVehicle.getModel());
        assertEquals(expectedLicensePlate, savedVehicle.getLicensePlate());
        assertEquals(expectedColor, savedVehicle.getColor());
        assertEquals(VehicleType.CAR.toString(), savedVehicle.getType().toString());
    }

    @Test
    void givenAnInvalidNullBrand_whenCallsCreateVehicle_shouldThrowException() {
        final var expectedModel = "Model";
        final var expectedLicensePlate = "31A2B3";
        final var expectedColor = "verde";
        final var expectedType = "CAR";
        final var expectedErrorMessage = "brand can not be null";

        final var vehicleRequest = new VehicleRequest(null, expectedModel, expectedLicensePlate, expectedColor, expectedType);

        final var exception = Assertions.assertThrows(NullFieldException.class, () -> useCase.execute(vehicleRequest));

        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(exception.getClass(), NullFieldException.class);

    }

    @Test
    void givenAnInvalidEmptyColor_whenCallsCreateVehicle_shouldThrowException() {
        final var expectedBrand = "Brand";
        final var expectedModel = "Model";
        final var expectedLicensePlate = "31A2B3";
        final var expectedColor = "";
        final var expectedType = "CAR";
        final var expectedErrorMessage = "color can not be empty";

        final var vehicleRequest = new VehicleRequest(expectedBrand, expectedModel, expectedLicensePlate, expectedColor, expectedType);

        final var exception = Assertions.assertThrows(BlankFieldException.class, () -> useCase.execute(vehicleRequest));

        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(exception.getClass(), BlankFieldException.class);

    }
}