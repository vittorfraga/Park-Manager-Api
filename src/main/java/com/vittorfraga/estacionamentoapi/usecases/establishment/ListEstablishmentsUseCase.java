package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class ListEstablishmentsUseCase extends UseCase<Pageable, Page<Establishment>> {

    private final EstablishmentRepository repository;


    public ListEstablishmentsUseCase(EstablishmentRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }


    @Override
    public Page<Establishment> execute(Pageable pageable) {
        return repository.findAll(pageable);
    }
}