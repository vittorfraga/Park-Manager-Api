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

//core business logic
public class ExitVehicleUseCaseImpl implements ExitVehicleUseCase {

    private final GetEstablishmentByIdUseCaseImpl getEstablishmentByIdUseCase;
    private final GetVehicleByLicensePlateUseCaseImpl getVehicleByLicensePlateUseCase;
    private final EstablishmentSlotsManagerGateway establishmentSlotsManagerGateway;
    private final EstablishmentAccessControlGateway establishmentAccessControlGateway;

    public ExitVehicleUseCaseImpl(GetEstablishmentByIdUseCaseImpl getEstablishmentByIdUseCase, GetVehicleByLicensePlateUseCaseImpl getVehicleByLicensePlateUseCase, EstablishmentSlotsManagerGateway establishmentSlotsManagerGateway, EstablishmentAccessControlGateway establishmentAccessControlGateway) {
        this.getEstablishmentByIdUseCase = getEstablishmentByIdUseCase;
        this.getVehicleByLicensePlateUseCase = getVehicleByLicensePlateUseCase;
        this.establishmentSlotsManagerGateway = establishmentSlotsManagerGateway;
        this.establishmentAccessControlGateway = establishmentAccessControlGateway;
    }

    @Override
    public void execute(AccessInput anInput) {
        //find the vehicle by license plate
        Vehicle vehicle = this.getVehicleByLicensePlateUseCase.execute(anInput.licensePlate());

        //find the establishment by id
        final var establishmentInput = new GetEstablishmentByIdInput(anInput.establishmentId());
        Establishment establishment = this.getEstablishmentByIdUseCase.execute(establishmentInput);

        //check if the vehicle is already inside by his last register in the database
        checkIfVehicleIsInside(anInput.licensePlate());

        // get or create a slots manager
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

        //create a new exit register for access control for the vehicle
        EstablishmentAccessControl newAccessControl = EstablishmentAccessControl.builder(
                establishment.getId(),
                vehicle.getLicensePlate(),
                VehicleEventType.EXIT,
                vehicle.getType()
        );


        establishmentAccessControlGateway.save(newAccessControl);

        updateDecreaseSlotsManager(slotsManager, newAccessControl.getVehicleType());
    }


    private void updateDecreaseSlotsManager(EstablishmentSlotsManager slotsManager, VehicleType vehicleType) {
        if (vehicleType == VehicleType.CAR) {
            slotsManager.decreaseOccupiedCarSlots();
        } else if (vehicleType == VehicleType.MOTORCYCLE) {
            slotsManager.decreaseOccupiedMotorcycleSlots();
        }
        establishmentSlotsManagerGateway.save(slotsManager);
    }

    private void checkIfVehicleIsInside(String licensePlate) {
        EstablishmentAccessControl lastAccessControl = establishmentAccessControlGateway.findLastAccessControlByLicensePlate(licensePlate);
        if (lastAccessControl != null && lastAccessControl.getEventType() != VehicleEventType.ENTRY) {
            throw new DomainException("Vehicle is not inside the establishment to make an exit");
        }
    }

}
