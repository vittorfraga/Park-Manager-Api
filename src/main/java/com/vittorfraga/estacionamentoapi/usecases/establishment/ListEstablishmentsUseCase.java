package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Use case for listing establishments with pagination.
 */
@Component
public class ListEstablishmentsUseCase extends UseCase<Pageable, Page<Establishment>> {

    private final EstablishmentRepository repository;

    /**
     * Constructs a ListEstablishmentsUseCase.
     *
     * @param repository The repository for establishments.
     */
    public ListEstablishmentsUseCase(EstablishmentRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    /**
     * Executes the use case to list establishments with pagination.
     *
     * @param pageable The pagination information.
     * @return A page containing a list of establishments.
     */
    @Override
    public Page<Establishment> execute(Pageable pageable) {
        return repository.findAll(pageable);
    }
}