package com.vittorfraga.estacionamentoapi.application.vehicle.update;

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

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateVehicleUseCaseImplTest {

    @InjectMocks
    private UpdateVehicleUseCaseImpl useCase;

    @Mock
    private VehicleGateway gateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(gateway);
    }

    @Test
    void givenAValidCommand_whenCallsUpdateVehicle_shouldReturnVehicleUpdated() {
        final var aVehicle = Vehicle.builder("brand", "model", "ABC1234", "green", VehicleType.CAR);
        final var expectedUpdatedBrand = "brand updated";
        final var expectedUpdatedModel = "model updated";
        final var expectedUpdatedLicensePlate = "ABC1234";
        final var expectedUpdatedColor = "color updated";
        final var expectedUpdatedType = VehicleType.MOTORCYCLE;

        final var Input = UpdateVehicleInput.builder(aVehicle.getId(), expectedUpdatedBrand, expectedUpdatedModel, expectedUpdatedLicensePlate, expectedUpdatedColor, expectedUpdatedType);

        when(gateway.findById(Mockito.eq(aVehicle.getId()))).thenReturn(Optional.of(aVehicle.clone()));

        when(gateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(Input);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.getId());


        verify(gateway, times(1)).findById((eq(actualOutput.getId())));

        verify(gateway, times(1)).update(argThat(updateVehicle ->
                Objects.equals(expectedUpdatedBrand, updateVehicle.getBrand())
                        && Objects.equals(expectedUpdatedModel, updateVehicle.getModel())
                        && Objects.equals(expectedUpdatedLicensePlate, updateVehicle.getLicensePlate())
                        && Objects.equals(expectedUpdatedColor, updateVehicle.getColor())
                        && Objects.equals(expectedUpdatedType, updateVehicle.getType())
        ));
    }


    @Test
    public void givenAnInvalidNullBrand_whenCallsUpdateVehicle_shouldUpdateAllTheOthersFields() {
        final var aVehicle = Vehicle.builder("brand", "model", "ABC1234", "green", VehicleType.CAR);

        final String expectedUpdatedBrand = null;
        final var expectedUpdatedModel = "model updated";
        final var expectedUpdatedLicensePlate = "ABC1234";
        final var expectedUpdatedColor = "color updated";
        final var expectedUpdatedType = VehicleType.MOTORCYCLE;
        final var expectedId = aVehicle.getId();

        final var input = UpdateVehicleInput.builder(
                aVehicle.getId(),
                expectedUpdatedBrand,
                expectedUpdatedModel,
                expectedUpdatedLicensePlate,
                expectedUpdatedColor,
                expectedUpdatedType
        );

        when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(aVehicle.clone()));

        when(gateway.update(any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));


        final var actualOutput = useCase.execute(input);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.getId());

        verify(gateway, times(1)).findById((eq(actualOutput.getId())));

        verify(gateway, times(1)).update(argThat(updateVehicle ->

                Objects.equals(aVehicle.getBrand(), updateVehicle.getBrand())
                        && Objects.equals(expectedUpdatedModel, updateVehicle.getModel())
                        && Objects.equals(expectedUpdatedLicensePlate, updateVehicle.getLicensePlate())
                        && Objects.equals(expectedUpdatedColor, updateVehicle.getColor())
                        && Objects.equals(expectedUpdatedType, updateVehicle.getType())
        ));
    }


    @Test
    public void givenAnInvalidId_whenCallsUpdateVehicle_shouldThrowException() {
        final var expectedUpdatedBrand = "brand updated";
        final var expectedUpdatedModel = "model updated";
        final var expectedUpdatedLicensePlate = "ABC1234";
        final var expectedUpdatedColor = "color updated";
        final var expectedUpdatedType = VehicleType.MOTORCYCLE;
        final var expectedId = "123";
        final var expectedErrorMessage = "Vehicle with ID %s was not found".formatted(expectedId);

        final var Input = UpdateVehicleInput.builder(expectedId, expectedUpdatedBrand, expectedUpdatedModel, expectedUpdatedLicensePlate, expectedUpdatedColor, expectedUpdatedType);

        when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(Input));

        Mockito.verify(gateway, times(0)).update(any());
        Mockito.verify(gateway, times(1)).findById(eq(expectedId));
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAnInvalidEmptyColor_whenCallsUpdateVehicle_thenShouldThrowsException() {
        final var aVehicle = Vehicle.builder("brand", "model", "ABC1234", "green", VehicleType.CAR);
        final var expectedUpdatedBrand = "brand updated";
        final var expectedUpdatedModel = "model updated";
        final var expectedUpdatedLicensePlate = "ABC1234";
        final String expectedUpdatedColor = "";
        final var expectedUpdatedType = VehicleType.MOTORCYCLE;
        final var expectedId = aVehicle.getId();
        final var expectedErrorMessage = "color can not be empty";

        final var Input = UpdateVehicleInput.builder(expectedId, expectedUpdatedBrand, expectedUpdatedModel, expectedUpdatedLicensePlate, expectedUpdatedColor, expectedUpdatedType);

        when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(aVehicle.clone()));

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(Input));

        Mockito.verify(gateway, times(0)).update(any());
        Mockito.verify(gateway, times(1)).findById(eq(expectedId));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());


    }
}