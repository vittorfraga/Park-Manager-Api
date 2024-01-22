package com.vittorfraga.estacionamentoapi.http;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.usecases.establishment.dtos.EstablishmentRequest;
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

@RequestMapping("establishments")
@Tag(name = "Establishments")
public interface EstablishmentAPI {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new establishment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Establishment created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
    })
    ResponseEntity<Establishment> createEstablishment(@Valid @RequestBody EstablishmentRequest input);

    @GetMapping
    @Operation(summary = "List establishments with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Establishments listed"),
    })
    ResponseEntity<Page<Establishment>> listEstablishments(Pageable pageable);


    @GetMapping({"/{id}"})
    @Operation(summary = "Get an establishment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Establishment found"),
            @ApiResponse(responseCode = "404", description = "Establishment not found"),
    })
    ResponseEntity<Establishment> getEstablishmentById(@PathVariable Long id);

    @PutMapping({"/{id}"})
    @Operation(summary = "Update an establishment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Establishment updated"),
            @ApiResponse(responseCode = "404", description = "Establishment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
    })
    ResponseEntity<Establishment> updateEstablishmentById(@PathVariable Long id, @Valid @RequestBody EstablishmentRequest input);

    @DeleteMapping({"/{id}"})
    @Operation(summary = "Delete an establishment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Establishment deleted"),
            @ApiResponse(responseCode = "404", description = "Establishment not found"),
    })
    ResponseEntity<Void> deleteEstablishmentById(@PathVariable Long id);
}

