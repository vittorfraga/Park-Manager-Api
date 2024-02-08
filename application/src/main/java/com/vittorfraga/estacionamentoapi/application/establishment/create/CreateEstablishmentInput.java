package com.vittorfraga.estacionamentoapi.application.establishment.create;

public record CreateEstablishmentInput(String name,
                                       String cnpj,
                                       String address,
                                       String phone,
                                       Integer motorcycleSlots,
                                       Integer carSlots) {
    public static CreateEstablishmentInput createInput(final String name,
                                                       final String cnpj,
                                                       final String address,
                                                       final String phone,
                                                       final Integer motorcycleSlots,
                                                       final Integer carSlots) {
        return new CreateEstablishmentInput(name, cnpj, address, phone, motorcycleSlots, carSlots);
    }
}
