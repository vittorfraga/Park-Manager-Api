package com.vittorfraga.estacionamentoapi.domain.vehicle;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private String licensePlate;
    private String color;
    private VehicleType type;

    public Vehicle(String brand, String model, String licensePlate, String color, VehicleType type) {
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.color = color;
        this.type = type;
        VehicleValidator.validateVehicleFields(this);
    }

    public Vehicle(Long id, String brand, String model, String licensePlate, String color, VehicleType type) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.color = color;
        this.type = type;
        VehicleValidator.validateVehicleFields(this);
    }

    public Vehicle update(String brand, String model, String licensePlate, String color, VehicleType type) {
        this.brand = (brand != null) ? brand : this.brand;
        this.model = (model != null) ? model : this.model;
        this.licensePlate = (licensePlate != null) ? licensePlate : this.licensePlate;
        this.color = (color != null) ? color : this.color;
        this.type = (type != null) ? type : this.type;

        VehicleValidator.validateVehicleFields(this);
        return this;
    }
}
