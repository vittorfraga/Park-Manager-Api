package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import com.vittorfraga.estacionamentoapi.domain.exceptions.ResourceNotFoundException;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GetEstablishmentByIdUseCase extends UseCase<Long, Establishment> {


    private final EstablishmentRepository repository;

    private final CacheManager cacheManager;

    public GetEstablishmentByIdUseCase(EstablishmentRepository repository, CacheManager cacheManager) {
        this.repository = Objects.requireNonNull(repository);
        this.cacheManager = cacheManager;
    }

    @Override
    public Establishment execute(Long establishmentId) {
        Cache cache = cacheManager.getCache("establishmentCache");
        Cache.ValueWrapper valueWrapper = cache.get(establishmentId);

        Establishment establishment;

        if (valueWrapper != null) {
            establishment = (Establishment) valueWrapper.get();
        } else {
            establishment = repository.findById(establishmentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Establishment", establishmentId));
            cache.put(establishmentId, establishment);
        }

        return establishment;
    }
}