package com.vittorfraga.estacionamentoapi.domain.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DetailedReport(
        @JsonProperty("report_description") String description,
        @JsonProperty("hour_format") String hourFormat,
        @JsonProperty("formatted_date") String date,
        @JsonProperty("detailed_reports") ReportsPerHour reports) {
}