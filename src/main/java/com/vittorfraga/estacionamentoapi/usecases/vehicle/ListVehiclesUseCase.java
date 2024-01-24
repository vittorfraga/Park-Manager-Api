package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ListVehiclesUseCase extends UseCase<Pageable, Page<Vehicle>> {

    private final VehicleRepository repository;

    public ListVehiclesUseCase(VehicleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Vehicle> execute(Pageable anInput) {
        return this.repository.findAll(anInput);
    }
}
