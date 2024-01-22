package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.usecases.UnitUseCase;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Use case for deleting an Establishment by its ID.
 */
@Component
public class DeleteEstablishmentUseCase extends UnitUseCase<Long> {

    private final EstablishmentRepository repository;
    private final GetEstablishmentByIdUseCase getEstablishmentByIdUseCase;

    /**
     * Constructs a DeleteEstablishmentUseCase.
     *
     * @param repository                  The repository for establishments.
     * @param getEstablishmentByIdUseCase The use case for retrieving an establishment by ID.
     */
    public DeleteEstablishmentUseCase(EstablishmentRepository repository, GetEstablishmentByIdUseCase getEstablishmentByIdUseCase) {
        this.repository = Objects.requireNonNull(repository);
        this.getEstablishmentByIdUseCase = Objects.requireNonNull(getEstablishmentByIdUseCase);
    }

    /**
     * Executes the use case to delete an establishment by its ID.
     *
     * @param anInput The ID of the establishment to be deleted.
     */
    @Override
    public void execute(Long anInput) {
        final var foundEstablishment = this.getEstablishmentByIdUseCase.execute(anInput);

        this.repository.deleteById(foundEstablishment.getId());
    }
}
