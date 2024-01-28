package com.vittorfraga.estacionamentoapi.web;

import com.vittorfraga.estacionamentoapi.usecases.parkingaccess.dto.AccessRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Access Parking Area Management")
public interface AccessManagementAPI {

    @PostMapping(value = "/entry", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle entry created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "400", description = "Vehicle must exit before making an entrance"),
            @ApiResponse(responseCode = "400", description = "No available slots"),
            @ApiResponse(responseCode = "404", description = "License plate not found"),
            @ApiResponse(responseCode = "404", description = "EstablishmentId not found"),

    })
    ResponseEntity<Boolean> entry(@Valid @RequestBody AccessRequest input);

    @PostMapping(value = "/exit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new exit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle exit created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "400", description = "The vehicle must enter before making an exit."),
            @ApiResponse(responseCode = "404", description = "License plate not found"),
            @ApiResponse(responseCode = "404", description = "EstablishmentId not found"),
    })
    ResponseEntity<Boolean> exit(@Valid @RequestBody AccessRequest input);
}