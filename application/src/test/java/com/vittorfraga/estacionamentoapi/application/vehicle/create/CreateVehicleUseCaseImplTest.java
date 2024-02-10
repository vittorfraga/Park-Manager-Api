package com.vittorfraga.estacionamentoapi.application.vehicle.create;

import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleGateway;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateVehicleUseCaseImplTest {

    @InjectMocks
    private CreateVehicleUseCaseImpl useCase;

    @Mock
    private VehicleGateway gateway;

    @Test
    public void givenAValidCommand_whenCallsCreateVehicle_shouldReturnNewEstablishment() {
        final var expectedBrand = "Brand";
        final var expectedModel = "Model";
        final var expectedLicensePlate = "ABC1234";
        final var expectedColor = "Color";
        final var expectedType = VehicleType.CAR;

        final var input = CreateVehicleInput.build(expectedBrand, expectedModel, expectedLicensePlate, expectedColor, expectedType);

        when(gateway.create(any()))
                .thenAnswer(returnsFirstArg());


        final var actualOutput = useCase.execute(input);


        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.getId());

        verify(gateway, times(1)).create(argThat(aVehicle ->
                Objects.equals(expectedBrand, aVehicle.getBrand())
                        && Objects.equals(expectedModel, aVehicle.getModel())
                        && Objects.equals(expectedLicensePlate, aVehicle.getLicensePlate())
                        && Objects.equals(expectedColor, aVehicle.getColor())
                        && Objects.equals(expectedType, aVehicle.getType())
                        && Objects.nonNull(aVehicle.getId())

        ));
    }

    @Test
    public void givenAnInvalidNullBrand_whenCallsCreateVehicle_shouldThrowException() {
        final String expectedBrand = null;
        final var expectedModel = "Model";
        final var expectedLicensePlate = "ABC1234";
        final var expectedColor = "Color";
        final var expectedType = VehicleType.CAR;
        final var expectedErrorMessages = "brand can not be null";

        final var input = CreateVehicleInput.build(expectedBrand, expectedModel, expectedLicensePlate, expectedColor, expectedType);

        final var Exception = Assertions.assertThrows(DomainException.class, () -> useCase.execute(input));

        Mockito.verify(gateway, times(0)).create(any());
        Assertions.assertEquals(expectedErrorMessages, Exception.getMessage());
        Assertions.assertEquals(Exception.getClass(), DomainException.class);

    }

    @Test
    public void givenAnInvalidEmptyModel_whenCallsCreateVehicle_shouldThrowException() {
        final String expectedBrand = "Brand";
        final var expectedModel = "";
        final var expectedLicensePlate = "ABC1234";
        final var expectedColor = "Color";
        final var expectedType = VehicleType.CAR;
        final var expectedErrorMessages = "model can not be empty";

        final var input = CreateVehicleInput.build(expectedBrand, expectedModel, expectedLicensePlate, expectedColor, expectedType);

        final var Exception = Assertions.assertThrows(DomainException.class, () -> useCase.execute(input));

        Mockito.verify(gateway, times(0)).create(any());
        Assertions.assertEquals(expectedErrorMessages, Exception.getMessage());
        Assertions.assertEquals(Exception.getClass(), DomainException.class);
    }


}