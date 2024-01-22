package com.vittorfraga.estacionamentoapi.domain.vehicle;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "tb_vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private String color;
    private String licensePlate;
    private VehicleType type;

    public Vehicle(String brand, String model, String color, String licensePlate, VehicleType type) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.licensePlate = licensePlate;
        this.type = type;
    }

}
