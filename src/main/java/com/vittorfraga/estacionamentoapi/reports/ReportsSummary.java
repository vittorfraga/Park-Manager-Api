package com.vittorfraga.estacionamentoapi.reports;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportsSummary {

    @JsonProperty("report_description")
    private String description;

    @JsonProperty("formatted_date")
    private String formattedDate;

    @JsonProperty("daily_report")
    private DailyReport dailyReport;

    public ReportsSummary(String description, String formattedDate, DailyReport dailyReport) {
        this.description = description;
        this.formattedDate = formattedDate;
        this.dailyReport = dailyReport;
    }
}