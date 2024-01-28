package com.vittorfraga.estacionamentoapi.usecases.reports.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReportsRequest(
        @NotNull(message = "establishmentId should not be null") Long establishmentId,
        @NotBlank(message = "dateString should not be null") String dateString) {
}
