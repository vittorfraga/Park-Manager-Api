package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.exceptions.ResourceNotFoundException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetVehicleByIdUseCaseTest {

    @InjectMocks
    private GetVehicleByIdUseCase useCase;

    @Mock
    private VehicleRepository repository;

    @Mock
    private CacheManager cacheManager;


    @Test
    void givenAnValidId_whenCallsGetVehicleById_thenShouldReturnAVehicle() {
        final var vehicleSaved = VehicleTestHelper.createAndSaveVehicle(repository);
        final var expectedId = vehicleSaved.getId();

        // Mocking cache behavior
        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("vehicleCache")).thenReturn(cache);
        when(cache.get(expectedId)).thenReturn(null);

        when(repository.findById(expectedId)).thenReturn(java.util.Optional.of(vehicleSaved));

        final var actualVehicle = useCase.execute(expectedId);

        
        verify(cache).get(expectedId);
        verify(cache).put(expectedId, actualVehicle);

      
        assertEquals(expectedId, actualVehicle.getId());
        assertEquals(vehicleSaved.getBrand(), actualVehicle.getBrand());
        assertEquals(vehicleSaved.getModel(), actualVehicle.getModel());
        assertEquals(vehicleSaved.getLicensePlate(), actualVehicle.getLicensePlate());
        assertEquals(vehicleSaved.getColor(), actualVehicle.getColor());
        assertEquals(vehicleSaved.getType(), actualVehicle.getType());
    }

    @Test
    void givenAInvalidId_whenCallsGetVehicleById_thenShouldThrowsException() {
        final var expectedId = 123L;
        final var expectedErrorMessage = "Could not find Vehicle with Id " + expectedId;

     
        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("vehicleCache")).thenReturn(cache);
        when(cache.get(expectedId)).thenReturn(null);

        when(repository.findById(eq(expectedId))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(ResourceNotFoundException.class, () -> useCase.execute(expectedId));

        verify(cache).get(expectedId);

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        Assertions.assertEquals(ResourceNotFoundException.class, actualException.getClass());
    }
}
