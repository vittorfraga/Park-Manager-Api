package com.vittorfraga.estacionamentoapi.domain.parkingaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstablishmentSlotsManagerRepository extends JpaRepository<EstablishmentSlotsManager, Long> {
    Optional<EstablishmentSlotsManager> findByEstablishmentId(Long establishmentId);
}
