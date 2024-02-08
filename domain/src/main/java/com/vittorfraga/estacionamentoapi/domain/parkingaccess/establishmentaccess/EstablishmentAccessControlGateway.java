package com.vittorfraga.estacionamentoapi.domain.parkingaccess.establishmentaccess;

public interface EstablishmentAccessControlGateway {
    EstablishmentAccessControl save(EstablishmentAccessControl establishmentAccessControl);

    EstablishmentAccessControl findLastRegister(String vehicleId);
}
