package com.vittorfraga.estacionamentoapi.domain.establishment;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Establishment that = (Establishment) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
