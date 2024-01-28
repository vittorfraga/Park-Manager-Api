package com.vittorfraga.estacionamentoapi.web.controllers;

import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.*;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.dto.UpdateVehicleRequest;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.dto.VehicleRequest;
import com.vittorfraga.estacionamentoapi.web.VehicleAPI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("vehicles")
public class VehicleController implements VehicleAPI {

    private final CreateVehicleUseCase createVehicleUseCase;
    private final ListVehiclesUseCase listVehiclesUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final DeleteVehicleUseCase deleteVehicleUseCase;
    private final GetVehicleByLicensePlateUseCase getVehicleByLicensePlateUseCase;

    public VehicleController(CreateVehicleUseCase createVehicleUseCase, GetVehicleByIdUseCase getVehicleByIdUseCase, ListVehiclesUseCase listVehiclesUseCase, UpdateVehicleUseCase updateVehicleUseCase, DeleteVehicleUseCase deleteVehicleUseCase, GetVehicleByLicensePlateUseCase getVehicleByLicensePlateUseCase) {
        this.createVehicleUseCase = Objects.requireNonNull(createVehicleUseCase);
        this.listVehiclesUseCase = Objects.requireNonNull(listVehiclesUseCase);
        this.updateVehicleUseCase = Objects.requireNonNull(updateVehicleUseCase);
        this.deleteVehicleUseCase = Objects.requireNonNull(deleteVehicleUseCase);
        this.getVehicleByLicensePlateUseCase = Objects.requireNonNull(getVehicleByLicensePlateUseCase);
    }

    @Override
    public ResponseEntity<Vehicle> createVehicle(VehicleRequest input) {
        final var vehicleCreated = this.createVehicleUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleCreated);
    }

    @Override
    public ResponseEntity<Page<Vehicle>> listVehicles(Pageable pageable) {
        final Page<Vehicle> vehicles = this.listVehiclesUseCase.execute(pageable);
        return ResponseEntity.ok(vehicles);
    }

//    @Override
//    public ResponseEntity<Vehicle> getVehicleById(Long id) {
//        Vehicle foundVehicle = this.getVehicleByIdUseCase.execute(id);
//        return ResponseEntity.ok(foundVehicle);
//    }

    @Override
    public ResponseEntity<Vehicle> getVehicleByLicensePlate(String licensePlate) {
        Vehicle foundVehicle = this.getVehicleByLicensePlateUseCase.execute(licensePlate);
        return ResponseEntity.ok(foundVehicle);
    }

    @Override
    public ResponseEntity<Vehicle> updateVehicle(Long id, UpdateVehicleRequest input) {
        final var updateVehicleInput = new UpdateVehicleRequest(
                id,
                input.brand(),
                input.model(),
                input.licensePlate(),
                input.color(),
                input.type()

        );

        final var updatedVehicle = this.updateVehicleUseCase.execute(updateVehicleInput);
        return ResponseEntity.ok(updatedVehicle);
    }

    @Override
    public ResponseEntity<Void> deleteVehicleById(Long id) {
        this.deleteVehicleUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
