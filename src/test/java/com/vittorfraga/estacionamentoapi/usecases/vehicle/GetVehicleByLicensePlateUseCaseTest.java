package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.vehicle.LicensePlateNotFoundException;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetVehicleByLicensePlateUseCaseTest {
    @InjectMocks
    private GetVehicleByLicensePlateUseCase useCase;

    @Mock
    private VehicleRepository repository;

    @Mock
    private CacheManager cacheManager;

    @Test
    void givenAnValidLicensePlate_whenCallsGetVehicleByLicensePlate_thenShouldReturnAVehicle() {
        final var vehicleSaved = VehicleTestHelper.createAndSaveVehicle(repository);
        final var expectedLicensePlate = vehicleSaved.getLicensePlate();

        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("vehicleCacheByLicensePlate")).thenReturn(cache); // Fix the stubbing argument

        when(cache.get(expectedLicensePlate)).thenReturn(null);

        when(repository.findByLicensePlate(expectedLicensePlate)).thenReturn(java.util.Optional.of(vehicleSaved));

        final var actualVehicle = useCase.execute(expectedLicensePlate);

        verify(cache).get(expectedLicensePlate);
        verify(cache).put(expectedLicensePlate, actualVehicle);

        assertEquals(vehicleSaved.getId(), actualVehicle.getId());
        assertEquals(vehicleSaved.getBrand(), actualVehicle.getBrand());
        assertEquals(vehicleSaved.getModel(), actualVehicle.getModel());
        assertEquals(vehicleSaved.getLicensePlate(), actualVehicle.getLicensePlate());
        assertEquals(vehicleSaved.getColor(), actualVehicle.getColor());
        assertEquals(vehicleSaved.getType(), actualVehicle.getType());
    }

    @Test
    void givenAInvalidLicensePlate_whenCallsGetVehicleByLicensePlate_thenShouldThrowsException() {
        final var expectedLicensePlate = "HS5N123L";
        final var expectedErrorMessage = "License plate " + expectedLicensePlate + " not found";

        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("vehicleCacheByLicensePlate")).thenReturn(cache);
        when(cache.get(expectedLicensePlate)).thenReturn(null);

        when(repository.findByLicensePlate(eq(expectedLicensePlate))).thenReturn(Optional.empty());


        final var actualException = Assertions.assertThrows(LicensePlateNotFoundException.class, () -> useCase.execute(expectedLicensePlate));

        verify(cache).get(expectedLicensePlate);

        assertEquals(expectedErrorMessage, actualException.getMessage());
        assertEquals(actualException.getClass(), LicensePlateNotFoundException.class);

    }
}