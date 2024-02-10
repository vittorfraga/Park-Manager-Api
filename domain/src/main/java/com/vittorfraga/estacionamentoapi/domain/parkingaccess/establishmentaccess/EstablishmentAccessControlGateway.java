package com.vittorfraga.estacionamentoapi.domain.parkingaccess.establishmentaccess;

public interface EstablishmentAccessControlGateway {
    void save(EstablishmentAccessControl establishmentAccessControl);

    EstablishmentAccessControl findLastAccessControlByLicensePlate(String licensePlate);
}
