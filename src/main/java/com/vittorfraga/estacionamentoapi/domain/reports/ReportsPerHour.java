package com.vittorfraga.estacionamentoapi.domain.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record ReportsPerHour(
        @JsonProperty("entry") Map<Integer, Integer> entriesPerHour,
        @JsonProperty("exit") Map<Integer, Integer> exitsPerHour) {
}
