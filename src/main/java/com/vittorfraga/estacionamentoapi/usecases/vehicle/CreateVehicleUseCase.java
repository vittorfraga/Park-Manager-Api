package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.vehicle.DuplicateLicensePlateException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.dto.VehicleRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CreateVehicleUseCase extends UseCase<VehicleRequest, Vehicle> {

    private final VehicleRepository repository;

    public CreateVehicleUseCase(VehicleRepository repository) {
        this.repository = Objects.requireNonNull(repository, "Repository cannot be null");
    }

    @Override
    public Vehicle execute(VehicleRequest anInput) {
        Vehicle vehicle = new Vehicle(
                anInput.brand(),
                anInput.model(),
                anInput.licensePlate(),
                anInput.color(),
                VehicleType.fromString(anInput.type())
        );

        System.out.println(" no use case-------licensePlate: " + vehicle.getLicensePlate());
        System.out.println(" no use case-------color: " + vehicle.getColor());

        Optional<Vehicle> licensePlateExists = this.repository.findByLicensePlate(vehicle.getLicensePlate());

        if (licensePlateExists.isPresent()) {
            throw new DuplicateLicensePlateException();
        }

        return this.repository.save(vehicle);
    }
}
