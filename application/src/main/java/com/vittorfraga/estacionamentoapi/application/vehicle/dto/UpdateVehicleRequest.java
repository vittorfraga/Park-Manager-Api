//package com.vittorfraga.estacionamentoapi.application.vehicle.dto;
//
//import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;
//import jakarta.validation.constraints.NotBlank;
//
//public record UpdateVehicleRequest(
//        Long id,
//        @NotBlank(message = "brand can not be empty") String brand,
//        @NotBlank(message = "model  can not be empty") String model,
//        @NotBlank(message = "licensePlate can not be empty") String licensePlate,
//        @NotBlank(message = "color can not be empty") String color,
//        @NotBlank(message = "type can not be empty") String type) {
//
//    public VehicleType getVehicleType() {
//        return VehicleType.fromString(type);
//    }
//}
