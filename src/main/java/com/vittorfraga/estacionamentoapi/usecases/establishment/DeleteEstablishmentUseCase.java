package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.usecases.UnitUseCase;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class DeleteEstablishmentUseCase extends UnitUseCase<Long> {

    private final EstablishmentRepository repository;
    private final GetEstablishmentByIdUseCase getEstablishmentByIdUseCase;


    public DeleteEstablishmentUseCase(EstablishmentRepository repository, GetEstablishmentByIdUseCase getEstablishmentByIdUseCase) {
        this.repository = Objects.requireNonNull(repository);
        this.getEstablishmentByIdUseCase = Objects.requireNonNull(getEstablishmentByIdUseCase);
    }


    @Override
    public void execute(Long anInput) {
        final var foundEstablishment = this.getEstablishmentByIdUseCase.execute(anInput);

        this.repository.deleteById(foundEstablishment.getId());
    }
}
