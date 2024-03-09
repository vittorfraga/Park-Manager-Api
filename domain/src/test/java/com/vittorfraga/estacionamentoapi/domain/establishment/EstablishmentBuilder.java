package com.vittorfraga.estacionamentoapi.domain.establishment;

import com.vittorfraga.estacionamentoapi.domain.validation.handler.ThrowsValidationHandler;

public class EstablishmentBuilder {
    private String name = "Establishment";
    private String cnpj = "12345678901234";
    private String address = "Rua ABC 123, CidadeX, PR";
    private String phone = "+55 123456789";
    private Integer motorCycleSlots = 20;
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

    public EstablishmentBuilder withMotorCycleSlots(Integer motorCycleSlots) {
        this.motorCycleSlots = motorCycleSlots;
        return this;
    }

    public EstablishmentBuilder withCarSlots(Integer carSlots) {
        this.carSlots = carSlots;
        return this;
    }

    public Establishment build() {
        Establishment establishment = Establishment.builder(name, cnpj, address, phone, motorCycleSlots, carSlots);
        establishment.validate(new ThrowsValidationHandler());
        return establishment;
    }
}
