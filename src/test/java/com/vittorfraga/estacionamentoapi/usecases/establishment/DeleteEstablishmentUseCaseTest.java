package com.vittorfraga.estacionamentoapi.usecases.establishment;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class DeleteEstablishmentUseCaseTest {

    @Autowired
    private EstablishmentRepository repository;

    @Autowired
    private DeleteEstablishmentUseCase useCase;

    @Test
    void givenAValidId_whenCallsDeleteEstablishmentUseCase_thenShouldDeleteEstablishment() {
        final var expectedName = "Estabelecimento Teste";
        final var expectedCnpj = "12345678901234";
        final var expectedAddress = "Endereço Teste";
        final var expectedPhone = "123456789";
        final var expectedMotorcycleSlots = 10;
        final var expectedCarSlots = 20;

        final var anEstablishment = new Establishment(
                expectedName,
                expectedCnpj,
                expectedAddress,
                expectedPhone,
                expectedMotorcycleSlots,
                expectedCarSlots);

        final var createdEstablishment = this.repository.save(anEstablishment);

        Assertions.assertNotNull(createdEstablishment);
        Assertions.assertEquals(1, this.repository.count());

        this.useCase.execute(createdEstablishment.getId());

        Assertions.assertEquals(0, this.repository.count());
        Assertions.assertNull(repository.findById(createdEstablishment.getId()).orElse(null));

    }

}
