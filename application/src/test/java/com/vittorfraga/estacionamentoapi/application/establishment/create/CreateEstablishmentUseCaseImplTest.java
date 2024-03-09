package com.vittorfraga.estacionamentoapi.application.establishment.create;

import com.vittorfraga.estacionamentoapi.domain.establishment.EstablishmentGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateEstablishmentUseCaseImplTest {

    @InjectMocks
    private CreateEstablishmentUseCaseImpl useCase;

    @Mock
    private EstablishmentGateway gateway;

    @Test
    public void givenAValidCommand_whenCallsCreateEstablishment_shouldReturnEstablishmentId() {
        final var expectedName = "Establishment";
        final var expectedCNPJ = "12345678901234";
        final var expectedAddress = ("Rua ABC 123, CidadeX, PR");
        final var expectedPhone = "+55 123456789";
        final var expectedMotorcycleSlots = 20;
        final var expectedCarSlots = 30;

        final var input = new CreateEstablishmentInput(expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorcycleSlots, expectedCarSlots);

        when(gateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(input);


        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.getId());

        verify(gateway, times(1)).create(argThat(anEstablishment ->
                Objects.equals(expectedName, anEstablishment.getName())
                        && Objects.equals(expectedCNPJ, anEstablishment.getCnpj())
                        && Objects.equals(expectedAddress, anEstablishment.getAddress())
                        && Objects.equals(expectedPhone, anEstablishment.getPhone())
                        && Objects.equals(expectedMotorcycleSlots, anEstablishment.getMotorcycleSlots())
                        && Objects.equals(expectedCarSlots, anEstablishment.getCarSlots())
                        && Objects.nonNull(anEstablishment.getId())

        ));
    }

    @Test
    public void givenAnInvalidName_whenCallsCreateEstablishment_shouldThrowException() {
        final var expectedName = "Es";
        final var expectedCNPJ = "12345678901234";
        final var expectedAddress = ("Rua ABC 123, CidadeX, PR");
        final var expectedPhone = "+55 123456789";
        final var expectedMotorCycleSlots = 20;
        final var expectedCarSlots = 30;
        final var expectedErrorMessages = "name must be between 3 and 255 characters";

        final var input = new CreateEstablishmentInput(expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorCycleSlots, expectedCarSlots);

        final var Exception = Assertions.assertThrows(DomainException.class, () -> useCase.execute(input));

        Mockito.verify(gateway, times(0)).create(any());
        Assertions.assertEquals(expectedErrorMessages, Exception.getMessage());
        Assertions.assertEquals(Exception.getClass(), DomainException.class);


    }

    @Test
    public void givenAnInvalidCNPJ_whenCallsCreateEstablishment_shouldThrowException() {
        final var expectedName = "Establishment";
        final var expectedCNPJ = "1234567890123";
        final var expectedAddress = ("Rua ABC 123, CidadeX, PR");
        final var expectedPhone = "+55 123456789";
        final var expectedMotorCycleSlots = 20;
        final var expectedCarSlots = 30;
        final var expectedErrorMessages = "'cnpj' must be 14 characters";

        final var input = new CreateEstablishmentInput(expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorCycleSlots, expectedCarSlots);

        final var Exception = Assertions.assertThrows(DomainException.class, () -> useCase.execute(input));

        Mockito.verify(gateway, times(0)).create(any());
        Assertions.assertEquals(expectedErrorMessages, Exception.getMessage());
        Assertions.assertEquals(Exception.getClass(), DomainException.class);

    }

    @Test
    public void givenANullPhone_whenCallsCreateEstablishment_shouldThrowException() {
        final var expectedName = "Establishment";
        final var expectedCNPJ = "12345678901234";
        final var expectedAddress = ("Rua ABC 123, CidadeX, PR");
        final String expectedPhone = null;
        final var expectedMotorCycleSlots = 20;
        final var expectedCarSlots = 30;
        final var expectedErrorMessages = "phone can not be null";

        final var input = new CreateEstablishmentInput(expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorCycleSlots, expectedCarSlots);

        final var Exception = Assertions.assertThrows(DomainException.class, () -> useCase.execute(input));

        Mockito.verify(gateway, times(0)).create(any());
        Assertions.assertEquals(expectedErrorMessages, Exception.getMessage());
        Assertions.assertEquals(Exception.getClass(), DomainException.class);
    }

    @Test
    void givenANegativeMotorCycleSlots_whenCallsCreateEstablishment_shouldThrowException() {
        final var expectedName = "Establishment";
        final var expectedCNPJ = "12345678901234";
        final var expectedAddress = ("Rua ABC 123, CidadeX, PR");
        final var expectedPhone = "+55 123456789";
        final var expectedMotorcycleSlots = -20;
        final var expectedCarSlots = 30;
        final var expectedErrorMessages = "motorcycleSlots should not be negative";

        final var input = new CreateEstablishmentInput(expectedName, expectedCNPJ, expectedAddress, expectedPhone, expectedMotorcycleSlots, expectedCarSlots);

        final var Exception = Assertions.assertThrows(DomainException.class, () -> useCase.execute(input));

        Mockito.verify(gateway, times(0)).create(any());
        Assertions.assertEquals(expectedErrorMessages, Exception.getMessage());
        Assertions.assertEquals(Exception.getClass(), DomainException.class);
    }

}