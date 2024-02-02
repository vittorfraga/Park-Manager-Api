package com.vittorfraga.estacionamentoapi.usecases.reports;

import com.vittorfraga.estacionamentoapi.domain.parkingaccess.VehicleEventType;
import com.vittorfraga.estacionamentoapi.domain.reports.DailyReport;
import com.vittorfraga.estacionamentoapi.domain.reports.ReportsQueryRepository;
import com.vittorfraga.estacionamentoapi.domain.reports.ReportsSummary;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import com.vittorfraga.estacionamentoapi.usecases.reports.dto.ReportsRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class GetDailySummaryUseCase extends UseCase<ReportsRequest, ReportsSummary> {

    private final ReportsQueryRepository repository;

    public GetDailySummaryUseCase(ReportsQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ReportsSummary execute(ReportsRequest request) {
        LocalDate day = getDay(request.dateString());
        int totalEntries = repository.countByEventTypeAndDay(request.establishmentId(), day, VehicleEventType.ENTRY);
        int totalExits = repository.countByEventTypeAndDay(request.establishmentId(), day, VehicleEventType.EXIT);

        String description = "Listing the total of entries and exits for the date";
        String formattedDate = day.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        DailyReport reports = new DailyReport(totalEntries, totalExits);

        return new ReportsSummary(description, formattedDate, reports);
    }

    private LocalDate getDay(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return LocalDate.now();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
            return LocalDate.parse(dateString, formatter);
        }
    }
}
