package com.vittorfraga.estacionamentoapi.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetailedReport {
    @JsonProperty("report_description")
    private String description;

    @JsonProperty("hour_format")
    private String hourFormat;

    @JsonProperty("formatted_date")
    private String date;

    @JsonProperty("reports_data")
    private ReportsData reports;

    public DetailedReport() {
    }

    public DetailedReport(String description, String hourFormat, String date, ReportsData reports) {
        this.description = description;
        this.hourFormat = hourFormat;
        this.date = date;
        this.reports = reports;
    }

}
