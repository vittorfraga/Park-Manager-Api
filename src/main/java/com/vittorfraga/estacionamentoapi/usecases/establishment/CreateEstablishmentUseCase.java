package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import com.vittorfraga.estacionamentoapi.usecases.establishment.dtos.EstablishmentRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Use case for creating a new Establishment.
 */
@Component
public class CreateEstablishmentUseCase extends UseCase<EstablishmentRequest, Establishment> {

    private final EstablishmentRepository repository;

    /**
     * Constructs a CreateEstablishmentUseCase.
     *
     * @param repository The repository for establishments.
     */
    public CreateEstablishmentUseCase(EstablishmentRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    /**
     * Executes the use case to create a new establishment.
     *
     * @param anInput The input containing details for creating the establishment.
     * @return The created establishment.
     * @throws IllegalArgumentException If motorcycleSlots or carSlots are negative.
     * @throws NullPointerException     If any of the input fields (name, cnpj, address, phone) is null.
     */
    @Override
    public Establishment execute(EstablishmentRequest anInput) {

        if (anInput.motorcycleSlots() < 0) {
            throw new IllegalArgumentException("motorcycleSlots must be non-negative");
        }

        if (anInput.carSlots() < 0) {
            throw new IllegalArgumentException("carSlots must be non-negative");
        }

        Establishment establishment = new Establishment(
                anInput.name(),
                anInput.cnpj(),
                anInput.address(),
                anInput.phone(),
                anInput.motorcycleSlots(),
                anInput.carSlots()
        );


        return this.repository.save(establishment);
    }
}