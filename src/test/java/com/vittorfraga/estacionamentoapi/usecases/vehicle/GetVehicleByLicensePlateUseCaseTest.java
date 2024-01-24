package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.vehicle.LicensePlateNotFoundException;
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
class GetVehicleByLicensePlateUseCaseTest {
    @InjectMocks
    private GetVehicleByLicensePlateUseCase useCase;

    @Mock
    private VehicleRepository repository;

    @Test
    void givenAnValidLicensePlate_whenCallsGetVehicleByLicensePlate_thenShouldReturnAVehicle() {
        final var vehicleSaved = VehicleTestHelper.createAndSaveVehicle(repository);

        when(repository.findByLicensePlate(vehicleSaved.getLicensePlate())).thenReturn(java.util.Optional.of(vehicleSaved));

        final var actualVehicle = useCase.execute(vehicleSaved.getLicensePlate());

        assertEquals(vehicleSaved.getId(), actualVehicle.getId());
        assertEquals(vehicleSaved.getBrand(), actualVehicle.getBrand());
        assertEquals(vehicleSaved.getModel(), actualVehicle.getModel());
        assertEquals(vehicleSaved.getLicensePlate(), actualVehicle.getLicensePlate());
        assertEquals(vehicleSaved.getColor(), actualVehicle.getColor());
        assertEquals(vehicleSaved.getType(), actualVehicle.getType());
    }

    @Test
    void givenAInvalidLicensePlate_whenCallsGetVehicleByLicensePlate_thenShouldThrowsException() {
        final var expectedLicensePlate = "HS5N123L";
        final var expectedErrorMessage = "License plate " + expectedLicensePlate + " not found";

        when(repository.findByLicensePlate(eq(expectedLicensePlate))).thenReturn(Optional.empty());


        final var actualException = Assertions.assertThrows(LicensePlateNotFoundException.class, () -> useCase.execute(expectedLicensePlate));

        assertEquals(expectedErrorMessage, actualException.getMessage());
        assertEquals(actualException.getClass(), LicensePlateNotFoundException.class);

    }
}