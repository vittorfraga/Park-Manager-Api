package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.domain.exceptions.ResourceNotFoundException;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Use case for retrieving an Establishment by its ID.
 */
@Component
public class GetEstablishmentByIdUseCase extends UseCase<Long, Establishment> {

    private final EstablishmentRepository repository;

    /**
     * Constructs a GetEstablishmentByIdUseCase.
     *
     * @param repository The repository for establishments.
     */
    public GetEstablishmentByIdUseCase(EstablishmentRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    /**
     * Executes the use case to retrieve an establishment by its ID.
     *
     * @param establishmentId The ID of the establishment to retrieve.
     * @return The retrieved establishment.
     * @throws ResourceNotFoundException If the establishment with the given ID is not found.
     */
    @Override
    public Establishment execute(Long establishmentId) {
        return this.repository.findById(establishmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Establishment", establishmentId));
    }
}