package com.vittorfraga.estacionamentoapi.usecases.parkingaccess;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.parkingaccess.EstablishmentSlotsManager;
import com.vittorfraga.estacionamentoapi.domain.parkingaccess.EstablishmentSlotsManagerRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

public class EstablishmentSlotsManagerTestHelper {
    public static EstablishmentSlotsManager createAndSaveEstablishmentSlotsManager(EstablishmentSlotsManagerRepository repository, Establishment establishment) {
        final var expectedMotorcycleSlots = 10;
        final var expectedCarSlots = 10;

        final var anEstablishmentSlotsManager = new EstablishmentSlotsManager(establishment.getId(), expectedMotorcycleSlots, expectedCarSlots, 0, 0);

        doAnswer(invocation -> {
            EstablishmentSlotsManager establishmentSlotsManagerToSave = invocation.getArgument(0);
            return new EstablishmentSlotsManager(1L, establishmentSlotsManagerToSave.getEstablishmentId(), establishmentSlotsManagerToSave.getMotorcycleSlots(), establishmentSlotsManagerToSave.getCarSlots(), 0, 0);
        }).when(repository).save(any(EstablishmentSlotsManager.class));

        return repository.save(anEstablishmentSlotsManager);
    }
}
