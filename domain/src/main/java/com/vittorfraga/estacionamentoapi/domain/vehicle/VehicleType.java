package com.vittorfraga.estacionamentoapi.domain.vehicle;

public enum VehicleType {
    CAR, MOTORCYCLE;

    public static VehicleType fromString(String type) {
        return switch (type.toLowerCase()) {
            case "car" -> CAR;
            case "motorcycle" -> MOTORCYCLE;
            default -> throw new IllegalArgumentException("Invalid vehicle type: " + type);
        };
    }

    public static String toString(VehicleType type) {
        return switch (type) {
            case CAR -> "car";
            case MOTORCYCLE -> "motorcycle";
            default -> throw new IllegalArgumentException("Invalid vehicle type: " + type);
        };
    }
}
