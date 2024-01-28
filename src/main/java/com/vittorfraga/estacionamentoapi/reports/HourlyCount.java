package com.vittorfraga.estacionamentoapi.reports;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HourlyCount {
    private Integer hour;
    private Integer count;

    public HourlyCount() {
    }

    public HourlyCount(Integer hour, Long count) {
        this.hour = hour;
        this.count = count.intValue();
    }

}

