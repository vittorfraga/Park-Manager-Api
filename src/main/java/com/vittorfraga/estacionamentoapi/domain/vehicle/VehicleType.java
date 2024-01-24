package com.vittorfraga.estacionamentoapi.domain.vehicle;

public enum VehicleType {
    CAR, MOTORCYCLE;

    public static VehicleType fromString(String type) {
        switch (type.toLowerCase()) {
            case "car":
                return CAR;
            case "motorcycle":
                return MOTORCYCLE;
            default:
                throw new IllegalArgumentException("Invalid vehicle type: " + type);
        }
    }
}
