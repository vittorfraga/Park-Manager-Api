package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.ResourceNotFoundException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.dto.UpdateVehicleRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UpdateVehicleUseCase extends UseCase<UpdateVehicleRequest, Vehicle> {

    private final VehicleRepository repository;

    public UpdateVehicleUseCase(VehicleRepository repository, GetVehicleByIdUseCase getVehicleByIdUseCase) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Vehicle execute(UpdateVehicleRequest anInput) {
        final var id = Objects.requireNonNull(anInput.id(), "id should not be null");
        final var brand = Objects.requireNonNull(anInput.brand(), "brand should not be null");
        final var model = Objects.requireNonNull(anInput.model(), "model should not be null");
        final var plate = Objects.requireNonNull(anInput.licensePlate(), "licensePlate should not be null");
        final var color = Objects.requireNonNull(anInput.color(), "color should not be null");
        final var type = Objects.requireNonNull(anInput.type(), "type should not be null");

        final var foundVehicle = this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("vehicle", id));

        foundVehicle.update(brand, model, plate, color, VehicleType.fromString(type));

        return this.repository.save(foundVehicle);
    }
}
