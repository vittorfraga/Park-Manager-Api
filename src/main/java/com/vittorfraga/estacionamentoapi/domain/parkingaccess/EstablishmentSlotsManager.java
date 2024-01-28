package com.vittorfraga.estacionamentoapi.domain.parkingaccess;

import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "slots_manager")
public class EstablishmentSlotsManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long establishmentId;
    private int motorcycleSlots;
    private int carSlots;
    private int currentOccupiedCarSlots;
    private int currentOccupiedMotorcycleSlots;


    public EstablishmentSlotsManager(Long establishmentId, int motorcycleSlots, int carSlots, int currentOccupiedCarSlots, int currentOccupiedMotorcycleSlots) {
        this.establishmentId = establishmentId;
        this.motorcycleSlots = motorcycleSlots;
        this.carSlots = carSlots;
        this.currentOccupiedCarSlots = currentOccupiedCarSlots;
        this.currentOccupiedMotorcycleSlots = currentOccupiedMotorcycleSlots;
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
}
