package com.vittorfraga.estacionamentoapi.domain.parkingaccess.establishmentaccess;

import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;

import java.time.LocalDateTime;
import java.util.UUID;

//this represents the access control of a vehicle in an establishment
public class EstablishmentAccessControl {

    private String id;
    private String vehicleLicensePlate;
    private String establishmentId;
    private VehicleEventType eventType;
    private VehicleType vehicleType;
    private LocalDateTime createdAt;


    private EstablishmentAccessControl(String id, String vehicleLicensePlate, String establishmentId, VehicleEventType eventType, VehicleType vehicleType) {
        this.id = id;
        this.vehicleLicensePlate = vehicleLicensePlate;
        this.establishmentId = establishmentId;
        this.eventType = eventType;
        this.createdAt = LocalDateTime.now();
        this.vehicleType = vehicleType;
    }

    public static EstablishmentAccessControl builder(
            final String vehicleLicensePlate,
            final String establishmentId,
            final VehicleEventType eventType,
            final VehicleType vehicleType) {
        return new EstablishmentAccessControl(
                UUID.randomUUID().toString(),
                vehicleLicensePlate,
                establishmentId,
                eventType,
                vehicleType);
    }

    public static EstablishmentAccessControl with(
            final String id,
            final String vehicleLicensePlate,
            final String establishmentId,
            final VehicleEventType eventType,
            final VehicleType vehicleType) {
        return new EstablishmentAccessControl(
                id,
                vehicleLicensePlate,
                establishmentId,
                eventType,
                vehicleType);
    }

    public String getId() {
        return id;
    }

    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    public String getEstablishmentId() {
        return establishmentId;
    }

    public VehicleEventType getEventType() {
        return eventType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


}