package com.vittorfraga.estacionamentoapi.application.establishment.update;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentGateway;
import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateEstablishmentUseCaseImplTest {

    @InjectMocks
    private UpdateEstablishmentUseCaseImpl useCase;

    @Mock
    private EstablishmentGateway gateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(gateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateEstablishment_shouldReturnEstablishmentId() {
        final var anEstablishment = Establishment.builder("name", "33333333333333", "Rua augusta 456, CidadeY, AM", "51987456321", 10, 10);
        final var expectedName = "Establishment";
        final var expectedCNPJ = "12345678901234";
        final var expectedAddress = "Rua ABC 123, CidadeX, PR";
        final var expectedPhone = "+55123456789";
        final var expectedMotorcycleSlots = 20;
        final var expectedCarSlots = 30;
        final var expectedId = anEstablishment.getId();

        final var Input = UpdateEstablishmentInput.createInput(anEstablishment.getId(), expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorcycleSlots, expectedCarSlots);

        when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(anEstablishment.clone()));

        when(gateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(Input);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.getId());
        System.out.println(actualOutput);

        verify(gateway, times(1)).findById((eq(expectedId)));

        verify(gateway, times(1)).update(argThat(updatedEstablishment ->
                Objects.equals(expectedName, updatedEstablishment.getName())
                        && Objects.equals(expectedCNPJ, updatedEstablishment.getCnpj())
                        && Objects.equals(expectedAddress, updatedEstablishment.getAddress())
                        && Objects.equals(expectedPhone, updatedEstablishment.getPhone())
                        && Objects.equals(expectedMotorcycleSlots, updatedEstablishment.getMotorcycleSlots())
                        && Objects.equals(expectedCarSlots, updatedEstablishment.getCarSlots())
                        && Objects.equals(expectedId, updatedEstablishment.getId())

        ));
    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateEstablishment_shouldThrowException() {
        final var anEstablishment = Establishment.builder("name", "33333333333333", "Rua augusta 456, CidadeY, AM", "51987456321", 10, 10);
        final var expectedName = "Es";
        final var expectedCNPJ = "12345678901234";
        final var expectedAddress = "Rua ABC 123, CidadeX, PR";
        final var expectedPhone = "+55123456789";
        final var expectedMotorcycleSlots = 20;
        final var expectedCarSlots = 30;
        final var expectedId = anEstablishment.getId();
        final var expectedErrorMessage = "name must be between 3 and 255 characters";

        final var Input = UpdateEstablishmentInput.createInput(expectedId, expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorcycleSlots, expectedCarSlots);

        when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(anEstablishment.clone()));

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(Input));


        Mockito.verify(gateway, times(0)).update(any());
        Mockito.verify(gateway, times(1)).findById(eq(expectedId));
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

    }

    @Test
    public void givenAnInvalidId_whenCallsUpdateEstablishment_shouldThrowException() {
        final var expectedName = "Establishment";
        final var expectedCNPJ = "12345678901234";
        final var expectedAddress = "Rua ABC 123, CidadeX, PR";
        final var expectedPhone = "+55123456789";
        final var expectedMotorcycleSlots = 20;
        final var expectedCarSlots = 30;
        final var expectedId = "123";
        final var expectedErrorMessage = "Establishment with ID %s was not found".formatted(expectedId);

        final var Input = UpdateEstablishmentInput.createInput(expectedId, expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorcycleSlots, expectedCarSlots);

        when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(Input));

        Mockito.verify(gateway, times(0)).update(any());
        Mockito.verify(gateway, times(1)).findById(eq(expectedId));
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    void givenAnInvalidCNPJ_whenCallsUpdateEstablishment_shouldThrowException() {
        final var anEstablishment = Establishment.builder("name", "33333333333333", "Rua augusta 456, CidadeY, AM", "51987456321", 10, 10);
        final var expectedName = "Establishment";
        final var expectedCNPJ = "1234567890123";
        final var expectedAddress = "Rua ABC 123, CidadeX, PR";
        final var expectedPhone = "+55123456789";
        final var expectedMotorcycleSlots = 20;
        final var expectedCarSlots = 30;
        final var expectedId = anEstablishment.getId();
        final var expectedErrorMessage = "'cnpj' must be 14 characters";

        final var Input = UpdateEstablishmentInput.createInput(anEstablishment.getId(), expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorcycleSlots, expectedCarSlots);

        when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(anEstablishment.clone()));

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(Input));

        Mockito.verify(gateway, times(0)).update(any());
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    void givenANegativeCarSlots_whenCallsUpdateEstablishment_shouldThrowException() {
        final var anEstablishment = Establishment.builder("name", "33333333333333", "Rua augusta 456, CidadeY, AM", "51987456321", 10, 10);
        final var expectedName = "Establishment";
        final var expectedCNPJ = "12345678901234";
        final var expectedAddress = "Rua ABC 123, CidadeX, PR";
        final var expectedPhone = "+55123456789";
        final var expectedMotorcycleSlots = 20;
        final var expectedCarSlots = -30;
        final var expectedId = anEstablishment.getId();
        final var expectedErrorMessage = "carSlots should not be negative";

        final var Input = UpdateEstablishmentInput.createInput(expectedId, expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorcycleSlots, expectedCarSlots);

        when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(anEstablishment.clone()));

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(Input));

        Mockito.verify(gateway, times(0)).update(any());
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

    }
}