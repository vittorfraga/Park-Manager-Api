package com.vittorfraga.estacionamentoapi.domain.parkingaccess.establishmentSlotManager;

public interface EstablishmentSlotsManagerGateway {

    EstablishmentSlotsManager findByEstablishmentId(String establishmentId);
}
