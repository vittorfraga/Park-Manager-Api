package com.vittorfraga.estacionamentoapi.domain.vehicle;

import com.vittorfraga.estacionamentoapi.domain.validation.ValidationHandler;
import com.vittorfraga.estacionamentoapi.domain.validation.Validator;

public class VehicleValidator extends Validator {

    private final Vehicle vehicle;

    protected VehicleValidator(final Vehicle aVehicle, final ValidationHandler aHandler) {
        super(aHandler);
        this.vehicle = aVehicle;
    }

    @Override
    public void validate() {
        validateNotNullOrBlank(this.vehicle.getBrand(), "brand");
        validateNotNullOrBlank(this.vehicle.getModel(), "model");
        validateNotNullOrBlank(this.vehicle.getColor(), "color");
        validateNotNullOrBlank(this.vehicle.getLicensePlate(), "licensePlate");
        validateNotNull(this.vehicle.getType(), "type");
    }

}

