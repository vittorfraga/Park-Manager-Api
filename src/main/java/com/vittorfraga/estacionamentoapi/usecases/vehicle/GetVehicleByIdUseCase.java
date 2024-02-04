package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.ResourceNotFoundException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GetVehicleByIdUseCase extends UseCase<Long, Vehicle> {


    private final VehicleRepository repository;
    private final CacheManager cacheManager;

    public GetVehicleByIdUseCase(VehicleRepository repository, CacheManager cacheManager) {
        this.repository = Objects.requireNonNull(repository);
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = "vehicleCache", key = "#anInput", unless = "#result == null")
    @Override
    public Vehicle execute(Long anInput) {
        Cache cache = cacheManager.getCache("vehicleCache");
        Cache.ValueWrapper valueWrapper = cache.get(anInput);

        Vehicle vehicle;

        if (valueWrapper != null) {
            vehicle = (Vehicle) valueWrapper.get();
        } else {
            vehicle = repository.findById(anInput)
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicle", anInput));
            cache.put(anInput, vehicle);
        }

        return vehicle;
    }
}
