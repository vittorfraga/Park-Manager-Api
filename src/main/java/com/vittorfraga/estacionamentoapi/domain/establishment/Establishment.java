package com.vittorfraga.estacionamentoapi.domain.establishment;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "establishment")
public class Establishment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cnpj;
    private String address;
    private String phone;
    private Integer motorcycleSlots;
    private Integer carSlots;

    public Establishment(String name, String cnpj, String address, String phone, Integer motorcycleSlots, Integer carSlots) {
        this.name = name;
        this.cnpj = cnpj;
        this.address = address;
        this.phone = phone;
        this.motorcycleSlots = motorcycleSlots;
        this.carSlots = carSlots;
        EstablishmentValidator.validateEstablishmentFields(this);
    }

    public Establishment(Long id, String name, String cnpj, String address, String phone, Integer motorcycleSlots, Integer carSlots) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.address = address;
        this.phone = phone;
        this.motorcycleSlots = motorcycleSlots;
        this.carSlots = carSlots;
        EstablishmentValidator.validateEstablishmentFields(this);
    }
}
