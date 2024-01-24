package com.vittorfraga.estacionamentoapi.domain.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.BlankFieldException;
import com.vittorfraga.estacionamentoapi.domain.exceptions.NullFieldException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VehicleTest {
    //Teste de criação Bem-Sucedida de um Vehicle
    //Teste de criação de um Vehicle com Brand nulo
    //Teste de criação de um Vehicle com Brand vazio
    //Teste de criação de um Vehicle com Model nulo
    //Teste de criação de um Vehicle com Color nulo
    //Teste de criação de um Vehicle com LicensePlate nulo
    //Teste de criação de um Vehicle com Type nulo


    @Test
    void givenValidParams_whenCallNewVehicle_thenShouldCreateNewVehicle() {
        final var actualVehicle = new VehicleBuilder().build();

        Assertions.assertNotNull(actualVehicle);
        Assertions.assertNotNull(actualVehicle.getBrand());
        Assertions.assertNotNull(actualVehicle.getModel());
        Assertions.assertNotNull(actualVehicle.getColor());
        Assertions.assertNotNull(actualVehicle.getLicensePlate());
    }

    @Test
    void givenNullBrand_whenCallNewVehicle_thenShouldThrowException() {
        var expectedErrorMessage = "brand can not be null";
        var expectedFieldName = "brand";

        VehicleBuilder builder = new VehicleBuilder().withBrand(null);

        var exception = Assertions.assertThrows(NullFieldException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());

    }

    @Test
    void givenEmptyBrand_whenCallNewVehicle_thenShouldThrowException() {
        var expectedErrorMessage = "brand can not be empty";
        var expectedFieldName = "brand";

        VehicleBuilder builder = new VehicleBuilder().withBrand("");

        var exception = Assertions.assertThrows(BlankFieldException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullModel_whenCallNewVehicle_thenShouldThrowException() {
        var expectedErrorMessage = "model can not be null";
        var expectedFieldName = "model";

        VehicleBuilder builder = new VehicleBuilder().withModel(null);

        var exception = Assertions.assertThrows(NullFieldException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullColor_whenCallNewVehicle_thenShouldThrowException() {
        var expectedErrorMessage = "color can not be null";
        var expectedFieldName = "color";

        VehicleBuilder builder = new VehicleBuilder().withColor(null);

        var exception = Assertions.assertThrows(NullFieldException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullLicensePlate_whenCallNewVehicle_thenShouldThrowException() {
        var expectedErrorMessage = "licensePlate can not be null";
        var expectedFieldName = "licensePlate";

        VehicleBuilder builder = new VehicleBuilder().withLicensePlate(null);

        var exception = Assertions.assertThrows(NullFieldException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

    @Test
    void givenNullType_whenCallNewVehicle_thenShouldThrowException() {
        var expectedErrorMessage = "type can not be null";
        var expectedFieldName = "type";

        VehicleBuilder builder = new VehicleBuilder().withType(null);

        var exception = Assertions.assertThrows(NullFieldException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(expectedFieldName, exception.getFieldName());
    }

}
