package com.vittorfraga.estacionamentoapi.domain.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DailyReport(
        @JsonProperty("total_entries") int totalEntries,
        @JsonProperty("total_exits") int totalExits) {
}
