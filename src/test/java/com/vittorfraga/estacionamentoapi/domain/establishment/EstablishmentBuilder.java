package com.vittorfraga.estacionamentoapi.domain.establishment;

public class EstablishmentBuilder {
    private String name = "Establishment";
    private String cnpj = "12345678901234";
    private String address = "Rua ABC 123, CidadeX, PR";
    private String phone = "+55 123456789";
    private Integer motorcycleSlots = 20;
    private Integer carSlots = 30;

    public EstablishmentBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public EstablishmentBuilder withCnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public EstablishmentBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public EstablishmentBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public EstablishmentBuilder withMotorcycleSlots(Integer motorcycleSlots) {
        this.motorcycleSlots = motorcycleSlots;
        return this;
    }

    public EstablishmentBuilder withCarSlots(Integer carSlots) {
        this.carSlots = carSlots;
        return this;
    }

    public Establishment build() {
        return new Establishment(name, cnpj, address, phone, motorcycleSlots, carSlots);
    }
}
