package com.vittorfraga.estacionamentoapi.domain.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VehicleTest {

    @Test
    void givenValidParams_whenCallNewVehicle_thenShouldCreateNewVehicle() {
        final var actualVehicle = new VehicleBuilder().build();

        Assertions.assertNotNull(actualVehicle);
        Assertions.assertNotNull(actualVehicle.getId());
        Assertions.assertNotNull(actualVehicle.getBrand());
        Assertions.assertNotNull(actualVehicle.getModel());
        Assertions.assertNotNull(actualVehicle.getColor());
        Assertions.assertNotNull(actualVehicle.getLicensePlate());
    }

    @Test
    void givenNullBrand_whenCallNewVehicle_thenShouldThrowException() {
        var expectedErrorMessage = "brand can not be null";
        final var expectedErrorCount = 1;

        VehicleBuilder builder = new VehicleBuilder().withBrand(null);

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());

    }

    @Test
    void givenEmptyBrand_whenCallNewVehicle_thenShouldThrowException() {
        var expectedErrorMessage = "brand can not be empty";
        final var expectedErrorCount = 1;

        VehicleBuilder builder = new VehicleBuilder().withBrand("");

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenNullModel_whenCallNewVehicle_thenShouldThrowException() {
        var expectedErrorMessage = "model can not be null";
        final var expectedErrorCount = 1;

        VehicleBuilder builder = new VehicleBuilder().withModel(null);

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenNullColor_whenCallNewVehicle_thenShouldThrowException() {
        var expectedErrorMessage = "color can not be null";
        final var expectedErrorCount = 1;

        VehicleBuilder builder = new VehicleBuilder().withColor(null);

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenNullLicensePlate_whenCallNewVehicle_thenShouldThrowException() {
        var expectedErrorMessage = "licensePlate can not be null";
        final var expectedErrorCount = 1;

        VehicleBuilder builder = new VehicleBuilder().withLicensePlate(null);

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenNullType_whenCallNewVehicle_thenShouldThrowException() {
        var expectedErrorMessage = "type can not be null";
        final var expectedErrorCount = 1;

        VehicleBuilder builder = new VehicleBuilder().withType(null);

        final var actualException = Assertions.assertThrows(DomainException.class, builder::build);

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }


    @Test
    void givenValidParams_whenCallUpdate_thenShouldUpdateVehicle() {
        final var actualVehicle = new VehicleBuilder().build();
        final var expectedBrand = "New Brand";
        final var expectedModel = "New Model";
        final var expectedColor = "New Color";
        final var expectedLicensePlate = "New LicensePlate";
        final var expectedType = VehicleType.MOTORCYCLE;

        actualVehicle.update(expectedBrand, expectedModel, expectedLicensePlate, expectedColor, expectedType);

        Assertions.assertEquals(expectedBrand, actualVehicle.getBrand());
        Assertions.assertEquals(expectedModel, actualVehicle.getModel());
        Assertions.assertEquals(expectedLicensePlate, actualVehicle.getLicensePlate());
        Assertions.assertEquals(expectedColor, actualVehicle.getColor());
        Assertions.assertEquals(expectedType, actualVehicle.getType());
    }

    @Test
    void givenNullParams_whenCallUpdate_thenShouldKeepCurrentValues() {
        final var originalBrand = "Original Brand";
        final var originalModel = "Original Model";
        final var originalColor = "Original Color";
        final var originalLicensePlate = "Original LicensePlate";
        final var originalType = VehicleType.CAR;

        final var actualVehicle = new VehicleBuilder()
                .withBrand(originalBrand)
                .withModel(originalModel)
                .withLicensePlate(originalLicensePlate)
                .withColor(originalColor)
                .withType(originalType)
                .build();

        actualVehicle.update(null, null, null, null, null);


        Assertions.assertEquals(originalBrand, actualVehicle.getBrand());
        Assertions.assertEquals(originalModel, actualVehicle.getModel());
        Assertions.assertEquals(originalLicensePlate, actualVehicle.getLicensePlate());
        Assertions.assertEquals(originalColor, actualVehicle.getColor());
        Assertions.assertEquals(originalType, actualVehicle.getType());
    }


}