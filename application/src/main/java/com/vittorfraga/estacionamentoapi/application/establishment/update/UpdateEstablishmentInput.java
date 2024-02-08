package com.vittorfraga.estacionamentoapi.application.establishment.update;

public record UpdateEstablishmentInput(
        String id,
        String name,
        String cnpj,
        String address,
        String phone,
        Integer motorcycleSlots,
        Integer carSlots
) {
    public static UpdateEstablishmentInput createInput(
            final String id,
            final String name,
            final String cnpj,
            final String address,
            final String phone,
            final Integer motorcycleSlots,
            final Integer carSlots
    ) {
        return new UpdateEstablishmentInput(id, name, cnpj, address, phone, motorcycleSlots, carSlots);
    }
}
