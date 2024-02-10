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

//core business logic
public class EntryVehicleUseCaseImpl implements EntryVehicleUseCase {
    private final GetEstablishmentByIdUseCaseImpl getEstablishmentByIdUseCase;
    private final GetVehicleByLicensePlateUseCaseImpl getVehicleByLicensePlateUseCase;
    private final EstablishmentSlotsManagerGateway establishmentSlotsManagerGateway;
    private final EstablishmentAccessControlGateway establishmentAccessControlGateway;

    public EntryVehicleUseCaseImpl(GetEstablishmentByIdUseCaseImpl getEstablishmentByIdUseCase, GetVehicleByLicensePlateUseCaseImpl getVehicleByLicensePlateUseCase, EstablishmentSlotsManagerGateway establishmentSlotsManagerGateway, EstablishmentAccessControlGateway establishmentAccessControlGateway) {
        this.getEstablishmentByIdUseCase = getEstablishmentByIdUseCase;
        this.getVehicleByLicensePlateUseCase = getVehicleByLicensePlateUseCase;
        this.establishmentSlotsManagerGateway = establishmentSlotsManagerGateway;
        this.establishmentAccessControlGateway = establishmentAccessControlGateway;
    }


    @Override
    public void execute(AccessInput anInput) {


        //find the establishment by id
        final var establishmentInput = new GetEstablishmentByIdInput(anInput.establishmentId());
        Establishment establishment = this.getEstablishmentByIdUseCase.execute(establishmentInput);

        //find the vehicle by license plate
        Vehicle vehicle = this.getVehicleByLicensePlateUseCase.execute(anInput.licensePlate());

        //check if the vehicle is already inside by his last register in the database
        checkIfVehicleAlreadyInside(anInput.licensePlate());

        //find the slots manager of the establishment
        EstablishmentSlotsManager slotsManager = establishmentSlotsManagerGateway.findByEstablishmentId(establishment.getId());

        //if the slots manager is null, create a new one
        if (slotsManager == null) {
            slotsManager = EstablishmentSlotsManager.builder(
                    establishment.getId(),
                    establishment.getMotorcycleSlots(),
                    establishment.getCarSlots(),
                    0,
                    0
            );
        }

        //check if there are available slots for the vehicle type
        checkAvailableSlots(slotsManager, vehicle.getType());

        //create a new register for access control for the vehicle
        EstablishmentAccessControl newAccessControl = EstablishmentAccessControl.builder(
                establishment.getId(),
                vehicle.getLicensePlate(),
                VehicleEventType.ENTRY,
                vehicle.getType()
        );


        establishmentAccessControlGateway.save(newAccessControl);

        updateIncreaseSlotsManager(slotsManager, newAccessControl.getVehicleType());
    }


    //this method checks if the vehicle is already inside the establishment
    private void checkIfVehicleAlreadyInside(String licensePlate) {
        EstablishmentAccessControl lastAccessControl = establishmentAccessControlGateway.findLastAccessControlByLicensePlate(licensePlate);
        if (lastAccessControl != null && lastAccessControl.getEventType() != VehicleEventType.EXIT) {
            throw new DomainException("Vehicle already inside");
        }
    }

    //this method checks if there are available slots giving the vehicle type and the slot manager
    private void checkAvailableSlots(EstablishmentSlotsManager slotsManager, VehicleType vehicleType) {
        int currentOccupiedSlots = (vehicleType == VehicleType.CAR) ?
                slotsManager.getCurrentOccupiedCarSlots() : slotsManager.getCurrentOccupiedMotorcycleSlots();

        if (currentOccupiedSlots >= ((vehicleType == VehicleType.CAR) ?
                slotsManager.getCarSlots() : slotsManager.getMotorcycleSlots())) {
            throw new NoAvailableSlotsException(vehicleType);
        }
    }

    //this method updates the slot manager increasing the occupied slots
    private void updateIncreaseSlotsManager(EstablishmentSlotsManager slotsManager, VehicleType vehicleType) {
        if (vehicleType == VehicleType.CAR) {
            slotsManager.increaseOccupiedCarSlots();
        } else if (vehicleType == VehicleType.MOTORCYCLE) {
            slotsManager.increaseOccupiedMotorcycleSlots();
        }

        establishmentSlotsManagerGateway.save(slotsManager);
    }
}
