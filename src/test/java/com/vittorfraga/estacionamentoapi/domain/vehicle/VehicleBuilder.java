package com.vittorfraga.estacionamentoapi.domain.vehicle;

public class VehicleBuilder {
    private String brand = "Brand";
    private String model = "Model";
    private String licensePlate = "ABC1234";
    private String color = "Color";
    private VehicleType type = VehicleType.CAR;

    public VehicleBuilder withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public VehicleBuilder withModel(String model) {
        this.model = model;
        return this;
    }

    public VehicleBuilder withColor(String color) {
        this.color = color;
        return this;
    }

    public VehicleBuilder withLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }

    public VehicleBuilder withType(VehicleType type) {
        this.type = type;
        return this;
    }

    public Vehicle build() {
        return new Vehicle(brand, model, licensePlate, color, type);
    }
}