package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EstablishmentTestHelper {

    public static Establishment createAndSaveEstablishment(EstablishmentRepository repository) {
        final var expectedName = "Name";
        final var expectedAddress = "Address";
        final var expectedCnpj = "12345678901234";
        final var expectedPhone = "12345678901";
        final var expectedMotorcycleSlots = 10;
        final var expectedCarSlots = 10;

        final var anEstablishment = new Establishment(expectedName, expectedCnpj, expectedPhone, expectedAddress, expectedMotorcycleSlots, expectedCarSlots);

        when(repository.save(any(Establishment.class))).thenAnswer(invocation -> {
            Establishment establishmentToSave = invocation.getArgument(0);
            return new Establishment(1L, establishmentToSave.getName(), establishmentToSave.getCnpj(), establishmentToSave.getAddress(), establishmentToSave.getPhone(), establishmentToSave.getMotorcycleSlots(), establishmentToSave.getCarSlots());
        });

        return repository.save(anEstablishment);
    }
}
