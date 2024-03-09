package com.vittorfraga.estacionamentoapi.domain.vehicle;


import com.vittorfraga.estacionamentoapi.domain.validation.ValidationHandler;

import java.util.UUID;


public class Vehicle implements Cloneable {

    private final String id;
    private String brand;
    private String model;
    private String licensePlate;
    private String color;
    private VehicleType type;

    private Vehicle(String id, String brand, String model, String licensePlate, String color, VehicleType type) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.color = color;
        this.type = type;

    }

    //when instantiating a new vehicle, the id is generated automatically
    public static Vehicle builder(final String aBrand,
                                  final String aModel,
                                  final String aLicensePlate,
                                  final String aColor,
                                  final VehicleType aType) {
        return new Vehicle(
                UUID.randomUUID().toString(),
                aBrand,
                aModel,
                aLicensePlate,
                aColor,
                aType);
    }

    //when the entity already exists, the id is passed as a parameter
    public static Vehicle with(final String anId,
                               final String aBrand,
                               final String aModel,
                               final String aLicensePlate,
                               final String aColor,
                               final VehicleType aType) {
        return new Vehicle(
                anId,
                aBrand,
                aModel,
                aLicensePlate,
                aColor,
                aType);
    }

    //update the vehicle fields
    public Vehicle update(String brand, String model, String licensePlate, String color, VehicleType type) {
        this.brand = (brand != null) ? brand : this.brand;
        this.model = (model != null) ? model : this.model;
        this.licensePlate = (licensePlate != null) ? licensePlate : this.licensePlate;
        this.color = (color != null) ? color : this.color;
        this.type = (type != null) ? type : this.type;

        return this;
    }

    public void validate(final ValidationHandler handler) {
        new VehicleValidator(this, handler).validate();
    }


    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getColor() {
        return color;
    }

    public VehicleType getType() {
        return type;
    }

    @Override
    public Vehicle clone() {
        try {
            return (Vehicle) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", color='" + color + '\'' +
                ", type=" + type +
                '}';
    }
}
