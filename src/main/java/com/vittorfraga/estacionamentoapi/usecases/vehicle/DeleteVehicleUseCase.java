package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.ResourceNotFoundException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import com.vittorfraga.estacionamentoapi.usecases.UnitUseCase;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DeleteVehicleUseCase extends UnitUseCase<Long> {

    private final VehicleRepository repository;

    public DeleteVehicleUseCase(VehicleRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public void execute(Long anInput) {
        if (!repository.existsById(anInput)) {
            throw new ResourceNotFoundException("vehicle", anInput);
        }

        repository.deleteById(anInput);
    }
}
