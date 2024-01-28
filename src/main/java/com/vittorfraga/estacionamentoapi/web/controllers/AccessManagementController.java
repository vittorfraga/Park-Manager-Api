package com.vittorfraga.estacionamentoapi.web.controllers;

import com.vittorfraga.estacionamentoapi.usecases.parkingaccess.EntryVehicleUseCase;
import com.vittorfraga.estacionamentoapi.usecases.parkingaccess.ExitVehicleUseCase;
import com.vittorfraga.estacionamentoapi.usecases.parkingaccess.dto.AccessRequest;
import com.vittorfraga.estacionamentoapi.web.AccessManagementAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessManagementController implements AccessManagementAPI {

    private final EntryVehicleUseCase entryVehicleUseCase;
    private final ExitVehicleUseCase exitVehicleUseCase;

    public AccessManagementController(EntryVehicleUseCase entryVehicleUseCase, ExitVehicleUseCase exitVehicleUseCase) {
        this.entryVehicleUseCase = entryVehicleUseCase;
        this.exitVehicleUseCase = exitVehicleUseCase;
    }

    @Override
    public ResponseEntity<Boolean> entry(AccessRequest input) {
        this.entryVehicleUseCase.execute(input);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Boolean> exit(AccessRequest input) {
        this.exitVehicleUseCase.execute(input);
        return ResponseEntity.noContent().build();
    }
}
