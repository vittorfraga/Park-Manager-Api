package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.ResourceNotFoundException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GetVehicleByIdUseCase extends UseCase<Long, Vehicle> {

    private final VehicleRepository repository;

    public GetVehicleByIdUseCase(VehicleRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Vehicle execute(Long anInput) {
        return this.repository.findById(anInput).orElseThrow(() -> new ResourceNotFoundException("vehicle", anInput));
    }
}
