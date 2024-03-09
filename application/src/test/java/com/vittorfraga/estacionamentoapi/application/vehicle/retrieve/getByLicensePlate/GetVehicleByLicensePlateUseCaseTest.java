package com.vittorfraga.estacionamentoapi.application.vehicle.retrieve.getByLicensePlate;

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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetVehicleByLicensePlateUseCaseTest {

    @InjectMocks
    private GetVehicleByLicensePlateUseCaseImpl useCase;

    @Mock
    private VehicleGateway vehicleGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(vehicleGateway);
    }

    @Test
    void givenAValidLicensePlate_whenCallsGetVehicleByLicensePlate_shouldReturnVehicle() {
        final var expectedBrand = "brand";
        final var expectedModel = "model";
        final var expectedLicensePlate = "ABC1234";
        final var expectedColor = "green";
        final var expectedType = VehicleType.CAR;

        final var aVehicle = Vehicle.builder(expectedBrand, expectedModel, expectedLicensePlate, expectedColor, expectedType);

        when(vehicleGateway.findByLicensePlate(expectedLicensePlate)).thenReturn(Optional.of(aVehicle));

        final var actualVehicle = useCase.execute(expectedLicensePlate);

        Assertions.assertNotNull(actualVehicle);
        Assertions.assertEquals(expectedLicensePlate, actualVehicle.getLicensePlate());
        Assertions.assertEquals(expectedBrand, actualVehicle.getBrand());
        Assertions.assertEquals(expectedModel, actualVehicle.getModel());
        Assertions.assertEquals(expectedColor, actualVehicle.getColor());
        Assertions.assertEquals(expectedType, actualVehicle.getType());

        verify(vehicleGateway, times(1)).findByLicensePlate((eq(expectedLicensePlate)));
    }

    @Test
    void givenAnInvalidLicensePlate_whenCallsGetVehicleByLicensePlate_shouldThrowException() {
        final var expectedLicensePlate = "ABC1234";
        final var expectedErrorMessage = "Vehicle with licensePlate ABC1234 was not found";

        when(vehicleGateway.findByLicensePlate(expectedLicensePlate)).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(expectedLicensePlate));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

    }

}