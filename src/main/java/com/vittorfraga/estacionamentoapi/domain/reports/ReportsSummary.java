package com.vittorfraga.estacionamentoapi.domain.reports;


import com.fasterxml.jackson.annotation.JsonProperty;

public record ReportsSummary(
        @JsonProperty("report_description") String description,
        @JsonProperty("formatted_date") String formattedDate,
        @JsonProperty("daily_report") DailyReport dailyReport) {

}