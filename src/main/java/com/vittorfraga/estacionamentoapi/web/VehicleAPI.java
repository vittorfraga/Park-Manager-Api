package com.vittorfraga.estacionamentoapi.web;

import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.dto.UpdateVehicleRequest;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.dto.VehicleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("vehicle")
@Tag(name = "Vehicles")
public interface VehicleAPI {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "400", description = "License plate already exists"),
            @ApiResponse(responseCode = "400", description = "'fieldName' can not be null"),

    })
    ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody VehicleRequest input);

    @GetMapping
    @Operation(summary = "List vehicles with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicles listed"),
    })
    ResponseEntity<Page<Vehicle>> listVehicles(Pageable pageable);

    @GetMapping("/{licensePlate}")
    @Operation(summary = "Get an vehicle by licence plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle found"),
            @ApiResponse(responseCode = "404", description = "Licenseplate not found"),
    })
    ResponseEntity<Vehicle> getVehicleByLicensePlate(@PathVariable String licensePlate);


    @PutMapping({"/{id}"})
    @Operation(summary = "Update a Vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle updated"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found"),
            @ApiResponse(responseCode = "400", description = "'fieldName' can not be null"),
            @ApiResponse(responseCode = "400", description = "License plate already exists"),
    })
    ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @Valid @RequestBody UpdateVehicleRequest input);

    @DeleteMapping({"/{id}"})
    @Operation(summary = "Delete a Vehicle by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehicle deleted"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found"),
    })
    ResponseEntity<Void> deleteVehicleById(@PathVariable Long id);

}
