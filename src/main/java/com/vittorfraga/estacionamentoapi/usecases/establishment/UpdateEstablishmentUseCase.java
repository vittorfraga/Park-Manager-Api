package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.domain.exceptions.establishment.NegativeSlotException;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import com.vittorfraga.estacionamentoapi.usecases.establishment.dtos.UpdateEstablishmentRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Use case for updating an existing Establishment.
 */
@Component
public class UpdateEstablishmentUseCase extends UseCase<UpdateEstablishmentRequest, Establishment> {

    private final EstablishmentRepository repository;
    private final GetEstablishmentByIdUseCase getEstablishmentByIdUseCase;

    /**
     * Constructs an UpdateEstablishmentUseCase.
     *
     * @param repository                  The repository for establishments.
     * @param getEstablishmentByIdUseCase The use case for retrieving an establishment by ID.
     */
    public UpdateEstablishmentUseCase(EstablishmentRepository repository, GetEstablishmentByIdUseCase getEstablishmentByIdUseCase) {
        this.repository = Objects.requireNonNull(repository);
        this.getEstablishmentByIdUseCase = Objects.requireNonNull(getEstablishmentByIdUseCase);
    }

    /**
     * Executes the use case to update an existing establishment.
     *
     * @param input The input containing details for updating the establishment.
     * @return The updated establishment.
     * @throws IllegalArgumentException If motorcycleSlots or carSlots are negative.
     * @throws NullPointerException     If any of the input fields (name, cnpj, address, phone) is null.
     */
    @Override
    public Establishment execute(UpdateEstablishmentRequest input) {
        if (input.motorcycleSlots() < 0) {
            throw new NegativeSlotException("motorcycleSlots");
        }

        if (input.carSlots() < 0) {
            throw new NegativeSlotException("carSlots");
        }

        final var actualEstablishment = this.getEstablishmentByIdUseCase.execute(input.id());

        final var name = Objects.requireNonNull(input.name(), "name should not be null");
        final var cnpj = Objects.requireNonNull(input.cnpj(), "cnpj should not be null");
        final var address = Objects.requireNonNull(input.address(), "address should not be null");
        final var phone = Objects.requireNonNull(input.phone(), "phone should not be null");
        final var motorcycleSlots = input.motorcycleSlots();
        final var carSlots = input.carSlots();

        actualEstablishment.setName(name);
        actualEstablishment.setCnpj(cnpj);
        actualEstablishment.setAddress(address);
        actualEstablishment.setPhone(phone);
        actualEstablishment.setMotorcycleSlots(motorcycleSlots);
        actualEstablishment.setCarSlots(carSlots);

        return this.repository.save(actualEstablishment);
    }
}
