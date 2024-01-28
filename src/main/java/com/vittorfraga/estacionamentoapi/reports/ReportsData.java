package com.vittorfraga.estacionamentoapi.reports;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ReportsData {

    private Map<Integer, Integer> entriesPerHour;
    private Map<Integer, Integer> exitsPerHour;

    public ReportsData() {
    }

    public ReportsData(Map<Integer, Integer> entriesPerHour, Map<Integer, Integer> exitsPerHour) {
        this.entriesPerHour = entriesPerHour;
        this.exitsPerHour = exitsPerHour;
    }


}
