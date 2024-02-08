package com.vittorfraga.estacionamentoapi.domain.parkingaccess.establishmentSlotManager;

import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;

import java.util.UUID;


//this represents the slots (motorcycleSlots and carSlots) of an establishment
public class EstablishmentSlotsManager {

    private String id;
    private String establishmentId;
    private int motorcycleSlots;
    private int carSlots;
    private int currentOccupiedCarSlots;
    private int currentOccupiedMotorcycleSlots;


    private EstablishmentSlotsManager(String id, String establishmentId, int motorcycleSlots, int carSlots, int currentOccupiedCarSlots, int currentOccupiedMotorcycleSlots) {
        this.id = id;
        this.establishmentId = establishmentId;
        this.motorcycleSlots = motorcycleSlots;
        this.carSlots = carSlots;
        this.currentOccupiedCarSlots = currentOccupiedCarSlots;
        this.currentOccupiedMotorcycleSlots = currentOccupiedMotorcycleSlots;
    }

    public static EstablishmentSlotsManager builder(
            final String establishmentId,
            final int motorcycleSlots,
            final int carSlots,
            final int currentOccupiedCarSlots,
            final int currentOccupiedMotorcycleSlots) {
        return new EstablishmentSlotsManager(
                UUID.randomUUID().toString(),
                establishmentId,
                motorcycleSlots,
                carSlots,
                currentOccupiedCarSlots,
                currentOccupiedMotorcycleSlots);
    }

    public static EstablishmentSlotsManager with(
            final String id,
            final String establishmentId,
            final int motorcycleSlots,
            final int carSlots,
            final int currentOccupiedCarSlots,
            final int currentOccupiedMotorcycleSlots) {
        return new EstablishmentSlotsManager(
                id,
                establishmentId,
                motorcycleSlots,
                carSlots,
                currentOccupiedCarSlots,
                currentOccupiedMotorcycleSlots);
    }


    public void increaseOccupiedCarSlots() {
        currentOccupiedCarSlots++;
    }

    public void decreaseOccupiedCarSlots() {
        if (currentOccupiedCarSlots > 0) {
            currentOccupiedCarSlots--;
        }
    }

    public void increaseOccupiedMotorcycleSlots() {
        currentOccupiedMotorcycleSlots++;
    }

    public void decreaseOccupiedMotorcycleSlots() {
        if (currentOccupiedMotorcycleSlots > 0) {
            currentOccupiedMotorcycleSlots--;
        }
    }

    public void decreaseOccupiedSlots(String vehicleType) {
        if (VehicleType.CAR.toString().equalsIgnoreCase(vehicleType)) {
            decreaseOccupiedCarSlots();
        } else if (VehicleType.MOTORCYCLE.toString().equalsIgnoreCase(vehicleType)) {
            decreaseOccupiedMotorcycleSlots();
        }
    }

    public String getId() {
        return id;
    }

    public String getEstablishmentId() {
        return establishmentId;
    }

    public int getMotorcycleSlots() {
        return motorcycleSlots;
    }

    public int getCarSlots() {
        return carSlots;
    }

    public int getCurrentOccupiedCarSlots() {
        return currentOccupiedCarSlots;
    }

    public int getCurrentOccupiedMotorcycleSlots() {
        return currentOccupiedMotorcycleSlots;
    }
}
