//package com.vittorfraga.estacionamentoapi.application.establishmentaccess;
//
//import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
//import com.vittorfraga.estacionamentoapi.domain.exceptions.parkingaccess.VehicleMustEnterException;
//import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;
//import com.vittorfraga.estacionamentoapi.usecases.UnitUseCase;
//import com.vittorfraga.estacionamentoapi.usecases.establishment.GetEstablishmentByIdUseCase;
//import com.vittorfraga.estacionamentoapi.usecases.vehicle.GetVehicleByLicensePlateUseCase;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Component
//public class ExitVehicleUseCaseImpl extends UnitUseCase<AccessInput> {
//
//    private final EstablishmentAccessControlRepository establishmentAccessControlRepository;
//    private final EstablishmentSlotsManagerRepository establishmentSlotsManagerRepository;
//    private final GetVehicleByLicensePlateUseCase getVehicleByLicensePlateUseCase;
//    private final GetEstablishmentByIdUseCase getEstablishmentByIdUseCase;
//
//    public ExitVehicleUseCase(EstablishmentAccessControlRepository establishmentAccessControlRepository,
//                              EstablishmentSlotsManagerRepository establishmentSlotsManagerRepository, GetVehicleByLicensePlateUseCase getVehicleByLicensePlateUseCase, GetEstablishmentByIdUseCase getEstablishmentByIdUseCase) {
//        this.establishmentAccessControlRepository = establishmentAccessControlRepository;
//        this.establishmentSlotsManagerRepository = establishmentSlotsManagerRepository;
//        this.getVehicleByLicensePlateUseCase = getVehicleByLicensePlateUseCase;
//        this.getEstablishmentByIdUseCase = getEstablishmentByIdUseCase;
//    }
//
//    @Override
//    public void execute(AccessInput anInput) {
//
//        var vehicle = this.getVehicleByLicensePlateUseCase.execute(anInput.licensePlate());
//
//        Establishment establishment = this.getEstablishmentByIdUseCase.execute(anInput.establishmentId());
//
//        Pageable pageable = PageRequest.of(0, 1);
//        List<EstablishmentAccessControl> lastAccessControl = establishmentAccessControlRepository.findLastRegister(anInput.licensePlate(), pageable);
//
//        if (lastAccessControl == null || lastAccessControl.isEmpty() || lastAccessControl.get(0).getEventType() != VehicleEventType.ENTRY) {
//            throw new VehicleMustEnterException();
//        }
//
//        EstablishmentSlotsManager slotsManager = getOrCreateSlotsManager(establishment);
//
//
//        EstablishmentAccessControl newAccessControl = new EstablishmentAccessControl(
//                establishment,
//                vehicle,
//                VehicleEventType.EXIT,
//                VehicleType.toString(vehicle.getType())
//        );
//
//        newAccessControl.setCreatedAt(LocalDateTime.now());
//
//        saveNewAccessControlAndSlotsManager(newAccessControl, slotsManager);
//    }
//
//    private void saveNewAccessControlAndSlotsManager(EstablishmentAccessControl newAccessControl, EstablishmentSlotsManager slotsManager) {
//        establishmentAccessControlRepository.save(newAccessControl);
//        updateDecreaseSlotsManager(slotsManager, newAccessControl.getVehicleType());
//    }
//
//
//    private EstablishmentSlotsManager getOrCreateSlotsManager(Establishment establishment) {
//        return establishmentSlotsManagerRepository.findByEstablishmentId(establishment.getId())
//                .orElse(new EstablishmentSlotsManager(establishment.getId(), establishment.getMotorcycleSlots(), establishment.getCarSlots(), 0, 0));
//    }
//
//    private void updateDecreaseSlotsManager(EstablishmentSlotsManager slotsManager, String vehicleType) {
//        slotsManager.decreaseOccupiedSlots(vehicleType);
//        establishmentSlotsManagerRepository.save(slotsManager);
//    }
//
//}
