package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.domain.exceptions.establishment.NegativeSlotException;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import com.vittorfraga.estacionamentoapi.usecases.establishment.dtos.UpdateEstablishmentRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UpdateEstablishmentUseCase extends UseCase<UpdateEstablishmentRequest, Establishment> {

    private final EstablishmentRepository repository;
    private final GetEstablishmentByIdUseCase getEstablishmentByIdUseCase;


    public UpdateEstablishmentUseCase(EstablishmentRepository repository, GetEstablishmentByIdUseCase getEstablishmentByIdUseCase) {
        this.repository = Objects.requireNonNull(repository);
        this.getEstablishmentByIdUseCase = Objects.requireNonNull(getEstablishmentByIdUseCase);
    }


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
