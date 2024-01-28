package com.vittorfraga.estacionamentoapi.usecases.parkingaccess;

import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.domain.parkingaccess.EstablishmentAccessControlRepository;
import com.vittorfraga.estacionamentoapi.domain.parkingaccess.EstablishmentSlotsManager;
import com.vittorfraga.estacionamentoapi.domain.parkingaccess.EstablishmentSlotsManagerRepository;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import com.vittorfraga.estacionamentoapi.usecases.establishment.EstablishmentTestHelper;
import com.vittorfraga.estacionamentoapi.usecases.establishment.GetEstablishmentByIdUseCase;
import com.vittorfraga.estacionamentoapi.usecases.parkingaccess.dto.AccessRequest;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.GetVehicleByLicensePlateUseCase;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.VehicleTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntryVehicleUseCaseTest {
    @InjectMocks
    EntryVehicleUseCase entryVehicleUseCase;
    @Mock
    GetEstablishmentByIdUseCase getEstablishmentByIdUseCase;
    @Mock
    GetVehicleByLicensePlateUseCase getVehicleByLicensePlateUseCase;
    @Mock
    EstablishmentSlotsManagerRepository establishmentSlotsManagerRepository;
    @Mock
    EstablishmentAccessControlRepository establishmentAccessControlRepository;

    @Mock
    EstablishmentRepository establishmentRepository;
    @Mock
    VehicleRepository vehicleRepository;

    @Test
    void givenAValidAccessRequest_whenCallsEntryVehicle_shouldSucceed() {
        final var vehicleSaved = VehicleTestHelper.createAndSaveVehicle(vehicleRepository);
        final var establishmentSaved = EstablishmentTestHelper.createAndSaveEstablishment(establishmentRepository);
        final var accessRequest = new AccessRequest(establishmentSaved.getId(), vehicleSaved.getLicensePlate());
        final var establishmentSlotsManager = new EstablishmentSlotsManager(establishmentSaved.getId(), establishmentSaved.getMotorcycleSlots(), establishmentSaved.getCarSlots(), 0, 0);

        when(getVehicleByLicensePlateUseCase.execute(anyString())).thenReturn(vehicleSaved);
        when(getEstablishmentByIdUseCase.execute(any())).thenReturn(establishmentSaved);
        when(establishmentSlotsManagerRepository.findByEstablishmentId(any())).thenReturn(Optional.of(establishmentSlotsManager));

        doAnswer(invocation -> {
            EstablishmentSlotsManager slotsManagerToSave = invocation.getArgument(0);
            return slotsManagerToSave;
        }).when(establishmentSlotsManagerRepository).save(any(EstablishmentSlotsManager.class));

        entryVehicleUseCase.execute(accessRequest);

        verify(getVehicleByLicensePlateUseCase, times(1)).execute(accessRequest.licensePlate());
        verify(getEstablishmentByIdUseCase, times(1)).execute(accessRequest.establishmentId());
        verify(establishmentAccessControlRepository, times(1)).save(any());
        verify(establishmentSlotsManagerRepository, times(1)).save(any());
    }


}
