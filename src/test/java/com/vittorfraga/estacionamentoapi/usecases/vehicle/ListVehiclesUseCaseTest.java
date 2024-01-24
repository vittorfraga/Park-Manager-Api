package com.vittorfraga.estacionamentoapi.usecases.vehicle;

import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListVehiclesUseCaseTest {
    @InjectMocks
    private ListVehiclesUseCase useCase;

    @Mock
    private VehicleRepository repository;


    @Test
    void givenAValidCommand_whenCallsListVehicles_thenShouldReturnAListOfVehicles() {
        final var vehicle1 = VehicleTestHelper.createAndSaveVehicle(repository);
        final var vehicle2 = VehicleTestHelper.createAndSaveVehicle(repository);
        final var vehicle3 = VehicleTestHelper.createAndSaveVehicle(repository);

        final var expectedVehicles = List.of(vehicle1, vehicle2, vehicle3);

        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(expectedVehicles));

        Pageable pageable = PageRequest.of(0, 2);

        final var actualVehicles = useCase.execute(pageable);

  
        assertEquals(expectedVehicles.size(), actualVehicles.getContent().size());
        assertEquals(expectedVehicles.get(0).getId(), actualVehicles.getContent().get(0).getId());
    }
}