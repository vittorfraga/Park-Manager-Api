package com.vittorfraga.estacionamentoapi.http.controllers;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.http.EstablishmentAPI;
import com.vittorfraga.estacionamentoapi.usecases.establishment.*;
import com.vittorfraga.estacionamentoapi.usecases.establishment.dtos.EstablishmentRequest;
import com.vittorfraga.estacionamentoapi.usecases.establishment.dtos.UpdateEstablishmentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("establishments")
public class EstablishmentController implements EstablishmentAPI {

    private final CreateEstablishmentUseCase createEstablishmentUseCase;
    private final GetEstablishmentByIdUseCase getEstablishmentByIdUseCase;
    private final ListEstablishmentsUseCase listEstablishmentsUseCase;
    private final UpdateEstablishmentUseCase updateEstablishmentUseCase;
    private final DeleteEstablishmentUseCase deleteEstablishmentUseCase;

    public EstablishmentController(CreateEstablishmentUseCase createEstablishmentUseCase, GetEstablishmentByIdUseCase getEstablishmentByIdUseCase, ListEstablishmentsUseCase listEstablishmentsUseCase, UpdateEstablishmentUseCase updateEstablishmentUseCase, DeleteEstablishmentUseCase deleteEstablishmentUseCase) {
        this.createEstablishmentUseCase = Objects.requireNonNull(createEstablishmentUseCase);
        this.getEstablishmentByIdUseCase = Objects.requireNonNull(getEstablishmentByIdUseCase);
        this.listEstablishmentsUseCase = Objects.requireNonNull(listEstablishmentsUseCase);
        this.updateEstablishmentUseCase = Objects.requireNonNull(updateEstablishmentUseCase);
        this.deleteEstablishmentUseCase = Objects.requireNonNull(deleteEstablishmentUseCase);
    }

    @Override
    public ResponseEntity<Establishment> createEstablishment(EstablishmentRequest input) {
        final var establishmentCreated = this.createEstablishmentUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(establishmentCreated);

    }

    @Override
    public ResponseEntity<Page<Establishment>> listEstablishments(Pageable pageable) {
        final Page<Establishment> establishmentsList = this.listEstablishmentsUseCase.execute(pageable);
        return ResponseEntity.ok(establishmentsList);
    }

    @Override
    public ResponseEntity<Establishment> getEstablishmentById(Long id) {
        Establishment foundEstablishment = this.getEstablishmentByIdUseCase.execute(id);
        return ResponseEntity.ok(foundEstablishment);
    }

    @Override
    public ResponseEntity<Establishment> updateEstablishmentById(Long id, EstablishmentRequest input) {
        final var updateEstablishmentInput = new UpdateEstablishmentRequest(
                id,
                input.name(),
                input.cnpj(),
                input.address(),
                input.phone(),
                input.motorcycleSlots(),
                input.carSlots()
        );

        final var updatedEstablishment = this.updateEstablishmentUseCase.execute(updateEstablishmentInput);
        return ResponseEntity.ok(updatedEstablishment);
    }

    @Override
    public ResponseEntity<Void> deleteEstablishmentById(Long id) {
        this.deleteEstablishmentUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
