package com.vittorfraga.estacionamentoapi.domain.establishment;


import java.util.Objects;
import java.util.UUID;


public class Establishment implements Cloneable {

    private String id;
    private String name;
    private String cnpj;
    private String address;
    private String phone;
    private Integer motorcycleSlots;
    private Integer carSlots;

    private Establishment(String id, String name, String cnpj, String address, String phone, Integer motorcycleSlots, Integer carSlots) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.address = address;
        this.phone = phone;
        this.motorcycleSlots = motorcycleSlots;
        this.carSlots = carSlots;
        EstablishmentValidator.validateEstablishmentFields(this);
    }

    //when instantiating a new establishment, the id is generated automatically
    public static Establishment builder(final String aName,
                                        final String aCnpj,
                                        final String anAddress,
                                        final String aPhone,
                                        final Integer aMotorcycleSlots,
                                        final Integer aCarSlots) {
        return new Establishment(
                UUID.randomUUID().toString(),
                aName,
                aCnpj,
                anAddress,
                aPhone,
                aMotorcycleSlots,
                aCarSlots);
    }

    //when the entity already exists, the id is passed as a parameter
    public static Establishment with(final String anId,
                                     final String aName,
                                     final String aCnpj,
                                     final String anAddress,
                                     final String aPhone,
                                     final Integer aMotorcycleSlots,
                                     final Integer aCarSlots) {
        return new Establishment(
                anId,
                aName,
                aCnpj,
                anAddress,
                aPhone,
                aMotorcycleSlots,
                aCarSlots);
    }

    //update method to change the establishment's fields
    public Establishment update(final String aName,
                                final String aCnpj,
                                final String anAddress,
                                final String aPhone,
                                final Integer aMotorcycleSlots,
                                final Integer aCarSlots) {
        this.name = (aName != null) ? aName : this.name;
        this.cnpj = (aCnpj != null) ? aCnpj : this.cnpj;
        this.address = (anAddress != null) ? anAddress : this.address;
        this.phone = (aPhone != null) ? aPhone : this.phone;
        this.motorcycleSlots = (aMotorcycleSlots != null) ? aMotorcycleSlots : this.motorcycleSlots;
        this.carSlots = (aCarSlots != null) ? aCarSlots : this.carSlots;
        EstablishmentValidator.validateEstablishmentFields(this);
        return this;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getMotorcycleSlots() {
        return motorcycleSlots;
    }

    public Integer getCarSlots() {
        return carSlots;
    }


    @Override
    public String toString() {
        return "Establishment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", motorcycleSlots=" + motorcycleSlots +
                ", carSlots=" + carSlots +
                '}';
    }

    @Override
    public Establishment clone() {
        try {
            return (Establishment) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Establishment that = (Establishment) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
