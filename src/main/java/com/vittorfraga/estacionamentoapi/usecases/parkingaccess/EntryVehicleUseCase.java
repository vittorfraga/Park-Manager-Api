package com.vittorfraga.estacionamentoapi.usecases.parkingaccess;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.exceptions.parkingaccess.NoAvailableSlotsException;
import com.vittorfraga.estacionamentoapi.domain.exceptions.parkingaccess.VehicleMustExitException;
import com.vittorfraga.estacionamentoapi.domain.parkingaccess.*;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;
import com.vittorfraga.estacionamentoapi.usecases.UnitUseCase;
import com.vittorfraga.estacionamentoapi.usecases.establishment.GetEstablishmentByIdUseCase;
import com.vittorfraga.estacionamentoapi.usecases.parkingaccess.dto.AccessRequest;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.GetVehicleByLicensePlateUseCase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EntryVehicleUseCase extends UnitUseCase<AccessRequest> {
    private final GetEstablishmentByIdUseCase getEstablishmentByIdUseCase;
    private final GetVehicleByLicensePlateUseCase getVehicleByLicensePlateUseCase;
    private final EstablishmentSlotsManagerRepository establishmentSlotsManagerRepository;
    private final EstablishmentAccessControlRepository establishmentAccessControlRepository;

    public EntryVehicleUseCase(GetEstablishmentByIdUseCase getEstablishmentByIdUseCase,
                               GetVehicleByLicensePlateUseCase getVehicleByLicensePlateUseCase,
                               EstablishmentSlotsManagerRepository establishmentSlotsManagerRepository,
                               EstablishmentAccessControlRepository establishmentAccessControlRepository) {
        this.getEstablishmentByIdUseCase = getEstablishmentByIdUseCase;
        this.getVehicleByLicensePlateUseCase = getVehicleByLicensePlateUseCase;
        this.establishmentSlotsManagerRepository = establishmentSlotsManagerRepository;
        this.establishmentAccessControlRepository = establishmentAccessControlRepository;
    }

    @Override
    public void execute(AccessRequest anInput) {
        var vehicle = this.getVehicleByLicensePlateUseCase.execute(anInput.licensePlate());
        String vehicleType = VehicleType.toString(vehicle.getType());

        Establishment establishment = this.getEstablishmentByIdUseCase.execute(anInput.establishmentId());


        checkIfVehicleAlreadyInside(anInput.licensePlate());

        EstablishmentSlotsManager slotsManager = getOrCreateSlotsManager(establishment);

        checkAvailableSlots(slotsManager, vehicleType);

        EstablishmentAccessControl newAccessControl = new EstablishmentAccessControl(
                establishment,
                vehicle,
                VehicleEventType.ENTRY,
                VehicleType.toString(vehicle.getType())
        );
        newAccessControl.setCreatedAt(LocalDateTime.now());

        saveNewAccessControlAndSlotsManager(newAccessControl, slotsManager);
    }

    private void checkIfVehicleAlreadyInside(String licensePlate) {
        Pageable pageable = PageRequest.of(0, 1);
        List<EstablishmentAccessControl> lastAccessControls = establishmentAccessControlRepository.findLastRegister(licensePlate, pageable);

        if (!lastAccessControls.isEmpty() && lastAccessControls.get(0).getEventType() != VehicleEventType.EXIT) {
            throw new VehicleMustExitException();
        }
    }


    private void saveNewAccessControlAndSlotsManager(EstablishmentAccessControl newAccessControl, EstablishmentSlotsManager slotsManager) {
        establishmentAccessControlRepository.save(newAccessControl);
        updateIncreaseSlotsManager(slotsManager, newAccessControl.getVehicleType());
    }


    private EstablishmentSlotsManager getOrCreateSlotsManager(Establishment establishment) {
        return establishmentSlotsManagerRepository.findByEstablishmentId(establishment.getId())
                .orElse(new EstablishmentSlotsManager(establishment.getId(), establishment.getMotorcycleSlots(), establishment.getCarSlots(), 0, 0));
    }

    private void checkAvailableSlots(EstablishmentSlotsManager slotsManager, String vehicleType) {
        int currentOccupiedSlots = (vehicleType.equalsIgnoreCase(VehicleType.CAR.toString())) ?
                slotsManager.getCurrentOccupiedCarSlots() : slotsManager.getCurrentOccupiedMotorcycleSlots();

        if (currentOccupiedSlots >= ((vehicleType.equalsIgnoreCase(VehicleType.CAR.toString())) ?
                slotsManager.getCarSlots() : slotsManager.getMotorcycleSlots())) {
            throw new NoAvailableSlotsException(vehicleType.toLowerCase());
        }
    }


    private void updateIncreaseSlotsManager(EstablishmentSlotsManager slotsManager, String vehicleType) {
        if (vehicleType.equalsIgnoreCase(VehicleType.CAR.toString())) {
            slotsManager.increaseOccupiedCarSlots();
        } else if (vehicleType.equalsIgnoreCase(VehicleType.MOTORCYCLE.toString())) {
            slotsManager.increaseOccupiedMotorcycleSlots();
        }

        establishmentSlotsManagerRepository.save(slotsManager);
    }
}
