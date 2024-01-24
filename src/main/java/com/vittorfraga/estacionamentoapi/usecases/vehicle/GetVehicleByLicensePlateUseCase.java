package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.vehicle.LicensePlateNotFoundException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GetVehicleByLicensePlateUseCase extends UseCase<String, Vehicle> {

    private final VehicleRepository repository;

    public GetVehicleByLicensePlateUseCase(VehicleRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Vehicle execute(String anInput) {
        return this.repository.findByLicensePlate(anInput).orElseThrow(() -> new LicensePlateNotFoundException(anInput));
    }
}
