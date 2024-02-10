package com.vittorfraga.estacionamentoapi.application.vehicle.retrieve.getByLicensePlate;

import com.vittorfraga.estacionamentoapi.application.UseCase;
import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;

public interface GetVehicleByLicensePlateUseCase extends UseCase<String, Vehicle> {
}
