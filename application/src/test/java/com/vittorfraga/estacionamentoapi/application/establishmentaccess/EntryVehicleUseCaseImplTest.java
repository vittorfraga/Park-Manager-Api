package com.vittorfraga.estacionamentoapi.application.establishmentaccess;

import com.vittorfraga.estacionamentoapi.application.establishment.retrieve.get.GetEstablishmentByIdInput;
import com.vittorfraga.estacionamentoapi.application.establishment.retrieve.get.GetEstablishmentByIdUseCaseImpl;
import com.vittorfraga.estacionamentoapi.application.vehicle.retrieve.getByLicensePlate.GetVehicleByLicensePlateUseCaseImpl;
import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;
import com.vittorfraga.estacionamentoapi.domain.exceptions.NoAvailableSlotsException;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntryVehicleUseCaseImplTest {

    @InjectMocks
    EntryVehicleUseCaseImpl useCase;
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
    void givenAValidCommand_whenCallsEntryVehicleWithCar_shouldBeOk() {
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
                0,
                0);

        when(establishmentSlotsManagerGateway.findByEstablishmentId(anEstablishment.getId())).thenReturn(anEstablishmentSlotsManager);

        //testing the use case
        final var accessInput = new AccessInput(anEstablishment.getId(), aVehicle.getLicensePlate());
        useCase.execute(accessInput);

        verify(establishmentAccessControlGateway, times(1)).save(any(EstablishmentAccessControl.class));
        verify(establishmentSlotsManagerGateway, times(1)).save(any(EstablishmentSlotsManager.class));

        Assertions.assertEquals(1, anEstablishmentSlotsManager.getCurrentOccupiedCarSlots());
        Assertions.assertNotEquals(0, anEstablishmentSlotsManager.getCurrentOccupiedCarSlots());
    }

    @Test
    void givenAValidCommand_whenCallsEntryVehicleWithMotorcycle_shouldBeOk() {
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
                0);

        when(establishmentSlotsManagerGateway.findByEstablishmentId(anEstablishment.getId())).thenReturn(anEstablishmentSlotsManager);


        final var accessInput = new AccessInput(anEstablishment.getId(), aVehicle.getLicensePlate());
        useCase.execute(accessInput);

        verify(establishmentAccessControlGateway, times(1)).save(any(EstablishmentAccessControl.class));
        verify(establishmentSlotsManagerGateway, times(1)).save(any(EstablishmentSlotsManager.class));

        Assertions.assertEquals(1, anEstablishmentSlotsManager.getCurrentOccupiedMotorcycleSlots());
        Assertions.assertNotEquals(0, anEstablishmentSlotsManager.getCurrentOccupiedMotorcycleSlots());
    }


    @Test
    void givenAnInvalidVehicleLicensePlate_whenCallsEntryVehicle_shouldThrowException() {
        final var vehicleLicensePlate = "123";
        final var expectedErrorMessage = "Vehicle with licensePlate ABC1234 was not found";

        final var anEstablishment = Establishment.builder("Establishment", "12345678901234", "Rua ABC 123, CidadeX, PR", "+55123456789", 20, 30);
        final var establishmentInput = new GetEstablishmentByIdInput(anEstablishment.getId());

        when(getEstablishmentByIdUseCase.execute(eq(establishmentInput))).thenReturn(anEstablishment);

        when(getVehicleByLicensePlateUseCase.execute(vehicleLicensePlate)).thenThrow(new DomainException(expectedErrorMessage));


        final var accessInput = new AccessInput(anEstablishment.getId(), vehicleLicensePlate);


        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(accessInput));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }


    @Test
    void givenAnInvalidVEstablishmentId_whenCallsEntryVehicle_shouldThrowException() {
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
    void givenAnInvalidCommandWithVehicleAlreadyInside_whenCallsEntryVehicle_shouldThrowException() {
        final var vehicleLicensePlate = "ABC1234";
        final var expectedErrorMessage = "Vehicle already inside";

        final var aVehicle = Vehicle.builder("brand", "model", vehicleLicensePlate, "green", VehicleType.CAR);
        final var anEstablishment = Establishment.builder("Establishment", "12345678901234", "Rua ABC 123, CidadeX, PR", "+55123456789", 20, 30);
        final var establishmentInput = new GetEstablishmentByIdInput(anEstablishment.getId());

        when(getVehicleByLicensePlateUseCase.execute(vehicleLicensePlate)).thenReturn(aVehicle);
        when(getEstablishmentByIdUseCase.execute(eq(establishmentInput))).thenReturn(anEstablishment);

        when(establishmentAccessControlGateway.findLastAccessControlByLicensePlate(vehicleLicensePlate)).thenReturn(EstablishmentAccessControl.builder(anEstablishment.getId(), vehicleLicensePlate, VehicleEventType.ENTRY, VehicleType.CAR));

        final var accessInput = new AccessInput(anEstablishment.getId(), vehicleLicensePlate);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(accessInput));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    void givenAValidCommandWithNoAvailableCarSlots_whenCallsEntryVehicle_shouldThrowException() {
        final var vehicleLicensePlate = "ABC1234";
        final var expectedErrorMessage = "No available car slots";

        final var aVehicle = Vehicle.builder("brand", "model", vehicleLicensePlate, "green", VehicleType.CAR);
        final var anEstablishment = Establishment.builder("Establishment", "12345678901234", "Rua ABC 123, CidadeX, PR", "+55123456789", 0, 0);
        final var establishmentInput = new GetEstablishmentByIdInput(anEstablishment.getId());

        when(getVehicleByLicensePlateUseCase.execute(vehicleLicensePlate)).thenReturn(aVehicle);
        when(getEstablishmentByIdUseCase.execute(eq(establishmentInput))).thenReturn(anEstablishment);

        final var anEstablishmentSlotsManager = EstablishmentSlotsManager.builder(
                anEstablishment.getId(),
                anEstablishment.getMotorcycleSlots(),
                anEstablishment.getCarSlots(),
                anEstablishment.getCarSlots(),
                0);

        when(establishmentSlotsManagerGateway.findByEstablishmentId(anEstablishment.getId())).thenReturn(anEstablishmentSlotsManager);

        final var accessInput = new AccessInput(anEstablishment.getId(), vehicleLicensePlate);

        final var actualException = Assertions.assertThrows(NoAvailableSlotsException.class, () -> useCase.execute(accessInput));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        Assertions.assertEquals(actualException.getClass(), NoAvailableSlotsException.class);

    }

    @Test
    void givenAValidCommandWithNoAvailableMotorcycleSlots_whenCallsEntryVehicle_shouldThrowException() {
        final var vehicleLicensePlate = "ABC1234";
        final var expectedErrorMessage = "No available motorcycle slots";

        final var aVehicle = Vehicle.builder("brand", "model", vehicleLicensePlate, "green", VehicleType.MOTORCYCLE);
        final var anEstablishment = Establishment.builder("Establishment", "12345678901234", "Rua ABC 123, CidadeX, PR", "+55123456789", 0, 0);
        final var establishmentInput = new GetEstablishmentByIdInput(anEstablishment.getId());

        when(getVehicleByLicensePlateUseCase.execute(vehicleLicensePlate)).thenReturn(aVehicle);
        when(getEstablishmentByIdUseCase.execute(eq(establishmentInput))).thenReturn(anEstablishment);

        final var anEstablishmentSlotsManager = EstablishmentSlotsManager.builder(
                anEstablishment.getId(),
                anEstablishment.getMotorcycleSlots(),
                anEstablishment.getCarSlots(),
                0,
                anEstablishment.getMotorcycleSlots());

        when(establishmentSlotsManagerGateway.findByEstablishmentId(anEstablishment.getId())).thenReturn(anEstablishmentSlotsManager);

        final var accessInput = new AccessInput(anEstablishment.getId(), vehicleLicensePlate);

        final var actualException = Assertions.assertThrows(NoAvailableSlotsException.class, () -> useCase.execute(accessInput));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        Assertions.assertEquals(actualException.getClass(), NoAvailableSlotsException.class);
    }


}