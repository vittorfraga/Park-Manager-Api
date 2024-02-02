package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.domain.exceptions.ResourceNotFoundException;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GetEstablishmentByIdUseCase extends UseCase<Long, Establishment> {

    private final EstablishmentRepository repository;


    public GetEstablishmentByIdUseCase(EstablishmentRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }


    @Override
    public Establishment execute(Long establishmentId) {
        return this.repository.findById(establishmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Establishment", establishmentId));
    }
}