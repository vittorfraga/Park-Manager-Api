package com.vittorfraga.estacionamentoapi.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyReport {
    @JsonProperty("total_entries")
    private int totalEntries;

    @JsonProperty("total_exits")
    private int totalExits;

    public DailyReport(int totalEntries, int totalExits) {
        this.totalEntries = totalEntries;
        this.totalExits = totalExits;
    }
}
