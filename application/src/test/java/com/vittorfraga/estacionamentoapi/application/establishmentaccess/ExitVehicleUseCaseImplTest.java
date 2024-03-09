package com.vittorfraga.estacionamentoapi.application.establishmentaccess;

import com.vittorfraga.estacionamentoapi.application.establishment.retrieve.get.GetEstablishmentByIdInput;
import com.vittorfraga.estacionamentoapi.application.establishment.retrieve.get.GetEstablishmentByIdUseCaseImpl;
import com.vittorfraga.estacionamentoapi.application.vehicle.retrieve.getByLicensePlate.GetVehicleByLicensePlateUseCaseImpl;
import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.parkingaccess.establishmentSlotManager.EstablishmentSlotsManager;
import com.vittorfraga.estacionamentoapi.domain.parkingaccess.establishmentSlotManager.EstablishmentSlotsManagerGateway;
import com.vittorfraga.estacionamentoapi.domain.parkingaccess.establishmentaccess.EstablishmentAccessControl;
import com.vittorfraga.estacionamentoapi.domain.parkingaccess.establishmentaccess.EstablishmentAccessControlGateway;
import com.vittorfraga.estacionamentoapi.domain.parkingaccess.establishmentaccess.VehicleEventType;
import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExitVehicleUseCaseImplTest {

    @InjectMocks
    ExitVehicleUseCaseImpl useCase;
    @Mock
    private GetEstablishmentByIdUseCaseImpl getEstablishmentByIdUseCase;
    @Mock
    private GetVehicleByLicensePlateUseCaseImpl getVehicleByLicensePlateUseCase;
    @Mock
    private EstablishmentSlotsManagerGateway establishmentSlotsManagerGateway;
    @Mock
    private EstablishmentAccessControlGateway establishmentAccessControlGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(establishmentSlotsManagerGateway, establishmentAccessControlGateway);
    }

    @Test
    void givenAValidCommand_whenCallsExitVehicleWithCar_shouldBeOk() {
        final var aVehicle = Vehicle.builder("brand", "model", "ABC1234", "green", VehicleType.CAR);
        final var anEstablishment = Establishment.builder("Establishment", "12345678901234", "Rua ABC 123, CidadeX, PR", "+55123456789", 20, 30);
        final var establishmentInput = new GetEstablishmentByIdInput(anEstablishment.getId());


        //simulating the vehicle and establishment being found through their use cases
        when(getVehicleByLicensePlateUseCase.execute(aVehicle.getLicensePlate())).thenReturn(aVehicle);

        when(getEstablishmentByIdUseCase.execute(eq(establishmentInput))).thenReturn(anEstablishment);


        //simulating the slots manager being found through the gateway
        final var anEstablishmentSlotsManager = EstablishmentSlotsManager.builder(
                anEstablishment.getId(),
                anEstablishment.getMotorcycleSlots(),
                anEstablishment.getCarSlots(),
                1,
                0);

        when(establishmentSlotsManagerGateway.findByEstablishmentId(anEstablishment.getId())).thenReturn(anEstablishmentSlotsManager);

        //testing the use case
        final var accessInput = new AccessInput(anEstablishment.getId(), aVehicle.getLicensePlate());
        useCase.execute(accessInput);

        verify(establishmentAccessControlGateway, times(1)).save(any(EstablishmentAccessControl.class));
        verify(establishmentSlotsManagerGateway, times(1)).save(any(EstablishmentSlotsManager.class));

        Assertions.assertEquals(0, anEstablishmentSlotsManager.getCurrentOccupiedCarSlots());
        Assertions.assertNotEquals(1, anEstablishmentSlotsManager.getCurrentOccupiedCarSlots());
    }

    @Test
    void givenAValidCommand_whenCallsExitVehicleWithMotorcycle_shouldBeOk() {
        final var aVehicle = Vehicle.builder("brand", "model", "ABC1234", "green", VehicleType.MOTORCYCLE);
        final var anEstablishment = Establishment.builder("Establishment", "12345678901234", "Rua ABC 123, CidadeX, PR", "+55123456789", 20, 30);
        final var establishmentInput = new GetEstablishmentByIdInput(anEstablishment.getId());

        //simulating the vehicle and establishment being found through their use cases
        when(getVehicleByLicensePlateUseCase.execute(aVehicle.getLicensePlate())).thenReturn(aVehicle);

        when(getEstablishmentByIdUseCase.execute(eq(establishmentInput))).thenReturn(anEstablishment);


        //simulating the slots manager being found through the gateway
        final var anEstablishmentSlotsManager = EstablishmentSlotsManager.builder(
                anEstablishment.getId(),
                anEstablishment.getMotorcycleSlots(),
                anEstablishment.getCarSlots(),
                0,
                1);

        when(establishmentSlotsManagerGateway.findByEstablishmentId(anEstablishment.getId())).thenReturn(anEstablishmentSlotsManager);


        final var accessInput = new AccessInput(anEstablishment.getId(), aVehicle.getLicensePlate());
        useCase.execute(accessInput);

        verify(establishmentAccessControlGateway, times(1)).save(any(EstablishmentAccessControl.class));
        verify(establishmentSlotsManagerGateway, times(1)).save(any(EstablishmentSlotsManager.class));

        Assertions.assertEquals(0, anEstablishmentSlotsManager.getCurrentOccupiedMotorcycleSlots());
        Assertions.assertNotEquals(1, anEstablishmentSlotsManager.getCurrentOccupiedMotorcycleSlots());
    }


    @Test
    void givenAnInvalidVehicleLicensePlate_whenCallsExitVehicle_shouldThrowException() {
        final var vehicleLicensePlate = "123";
        final var expectedErrorMessage = "Vehicle with licensePlate ABC1234 was not found";

        final var anEstablishment = Establishment.builder("Establishment", "12345678901234", "Rua ABC 123, CidadeX, PR", "+55123456789", 20, 30);
        final var establishmentInput = new GetEstablishmentByIdInput(anEstablishment.getId());

        //   when(getEstablishmentByIdUseCase.execute(eq(establishmentInput))).thenReturn(anEstablishment);

        when(getVehicleByLicensePlateUseCase.execute(vehicleLicensePlate)).thenThrow(new DomainException(expectedErrorMessage));


        final var accessInput = new AccessInput(anEstablishment.getId(), vehicleLicensePlate);


        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(accessInput));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }


    @Test
    void givenAnInvalidVEstablishmentId_whenCallsExitVehicle_shouldThrowException() {
        final var establishmentID = "123";
        final var expectedErrorMessage = "Establishment with ID 123 was not found";

        final var aVehicle = Vehicle.builder("brand", "model", "ABC1234", "green", VehicleType.CAR);
        final var getEsblishmentIdInput = new GetEstablishmentByIdInput(establishmentID);

        when(getEstablishmentByIdUseCase.execute(eq(getEsblishmentIdInput))).thenThrow(new DomainException(expectedErrorMessage));

        final var accessInput = new AccessInput(establishmentID, aVehicle.getLicensePlate());
        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(accessInput));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    void givenAnInvalidCommandWithVehicleAlreadyOutside_whenCallsExitVehicle_shouldThrowException() {
        final var vehicleLicensePlate = "ABC1234";
        final var expectedErrorMessage = "Vehicle is not inside the establishment to make an exit";

        final var aVehicle = Vehicle.builder("brand", "model", vehicleLicensePlate, "green", VehicleType.CAR);
        final var anEstablishment = Establishment.builder("Establishment", "12345678901234", "Rua ABC 123, CidadeX, PR", "+55123456789", 20, 30);
        final var establishmentInput = new GetEstablishmentByIdInput(anEstablishment.getId());

        when(getVehicleByLicensePlateUseCase.execute(vehicleLicensePlate)).thenReturn(aVehicle);
        when(getEstablishmentByIdUseCase.execute(eq(establishmentInput))).thenReturn(anEstablishment);

        when(establishmentAccessControlGateway.findLastAccessControlByLicensePlate(vehicleLicensePlate)).thenReturn(EstablishmentAccessControl.builder(anEstablishment.getId(), vehicleLicensePlate, VehicleEventType.EXIT, VehicleType.CAR));

        final var accessInput = new AccessInput(anEstablishment.getId(), vehicleLicensePlate);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(accessInput));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }


}