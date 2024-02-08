package com.vittorfraga.estacionamentoapi.domain.establishment;

import java.util.List;
import java.util.Optional;

public interface EstablishmentGateway {
    Establishment create(Establishment establishment);

    Optional<Establishment> findById(String id);

    List<Establishment> findAll();

    Establishment update(Establishment establishment);

    void delete(String id);
}
