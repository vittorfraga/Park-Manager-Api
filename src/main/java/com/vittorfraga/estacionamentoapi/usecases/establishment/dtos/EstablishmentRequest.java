package com.vittorfraga.estacionamentoapi.usecases.establishment.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record EstablishmentRequest(
        @NotBlank(message = "name can not be empty") String name,
        @NotBlank(message = "cnpj can not be empty") String cnpj,
        @NotBlank(message = "phone can not be empty") String phone,
        @NotBlank(message = "address can not be empty") String address,
        @NotNull(message = "motorcycleSlots can not be null") @PositiveOrZero(message = "motorcycleSlots must be positive") Integer motorcycleSlots,
        @NotNull(message = "carSlots can not be null") @PositiveOrZero(message = "carSlots must be positive") Integer carSlots
) {
}
