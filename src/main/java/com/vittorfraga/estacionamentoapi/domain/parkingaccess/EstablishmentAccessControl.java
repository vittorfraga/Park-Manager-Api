package com.vittorfraga.estacionamentoapi.domain.parkingaccess;

import com.vittorfraga.estacionamentoapi.domain.establishment.Establishment;
import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_table")
@NoArgsConstructor
@Getter
@Setter
public class EstablishmentAccessControl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_plate", referencedColumnName = "licensePlate")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "establishment_id", referencedColumnName = "id")
    private Establishment establishment;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type") //ENTRY or EXIT
    private VehicleEventType eventType;

    private String vehicleType;

    private LocalDateTime createdAt;


    public EstablishmentAccessControl(Establishment establishment, Vehicle vehicle, VehicleEventType eventType, String vehicleType) {
        this.establishment = establishment;
        this.vehicle = vehicle;
        this.eventType = eventType;
        this.createdAt = LocalDateTime.now();
        this.vehicleType = vehicleType;
    }

}