package com.vittorfraga.estacionamentoapi.usecases.establishment;


import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class ListEstablishmentsUseCaseTest {

    @Autowired
    private EstablishmentRepository repository;

    @Autowired
    private ListEstablishmentsUseCase useCase;


    @Test
    void givenAValidCommand_whenCallsListEstablishmentsUseCase_thenShouldReturnEstablishmentList() {


        final var establishment1 = new Establishment(
                "Estabelecimento Teste",
                "12345678901234",
                "Endereço Teste",
                "123456789",
                10,
                20);

        final var establishment2 = new Establishment(
                "Estabelecimento Teste 2",
                "12345678901234",
                "Endereço Teste 2",
                "123456789",
                30,
                20);

        List<Establishment> establishments = Arrays.asList(establishment1, establishment2);

        this.repository.saveAll(establishments);


        Pageable pageable = PageRequest.of(0, 2);

        final var establishmentList = useCase.execute(pageable);

        Assertions.assertEquals(2, establishmentList.getContent().size());
        Assertions.assertEquals(2, establishmentList.getTotalElements());
        Assertions.assertEquals(0, establishmentList.getNumber());
    }
}
