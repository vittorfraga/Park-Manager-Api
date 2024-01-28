package com.vittorfraga.estacionamentoapi.domain.parkingaccess;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface EstablishmentAccessControlRepository extends JpaRepository<EstablishmentAccessControl, Long> {
    Optional<EstablishmentAccessControl> findByVehicleLicensePlateAndEstablishmentId(String vehicleLicensePlate, Long establishmentId);

    @Query("SELECT eac FROM EstablishmentAccessControl eac WHERE eac.vehicle.licensePlate = :vehicleLicensePlate ORDER BY eac.id DESC")
    List<EstablishmentAccessControl> findLastRegister(@Param("vehicleLicensePlate") String vehicleLicensePlate, Pageable pageable);


}

