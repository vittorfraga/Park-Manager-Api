package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import com.vittorfraga.estacionamentoapi.usecases.establishment.dtos.EstablishmentRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class CreateEstablishmentUseCase extends UseCase<EstablishmentRequest, Establishment> {

    private final EstablishmentRepository repository;


    public CreateEstablishmentUseCase(EstablishmentRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }


    @Override
    public Establishment execute(EstablishmentRequest anInput) {

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