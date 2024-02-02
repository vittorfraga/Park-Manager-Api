package com.vittorfraga.estacionamentoapi.web.controllers;

// ReportsController.java

import com.vittorfraga.estacionamentoapi.domain.reports.DetailedReport;
import com.vittorfraga.estacionamentoapi.domain.reports.ReportsSummary;
import com.vittorfraga.estacionamentoapi.usecases.reports.GetDailySummaryUseCase;
import com.vittorfraga.estacionamentoapi.usecases.reports.GetHourlySummaryDetailsUseCase;
import com.vittorfraga.estacionamentoapi.usecases.reports.dto.ReportsRequest;
import com.vittorfraga.estacionamentoapi.web.ReportsAPI;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/establishments/reports")
public class ReportsController implements ReportsAPI {

    private final GetDailySummaryUseCase getDailySummaryUseCase;
    private final GetHourlySummaryDetailsUseCase getHourlySummaryDetailsUseCase;

    public ReportsController(GetDailySummaryUseCase getDailySummaryUseCase,
                             GetHourlySummaryDetailsUseCase getHourlySummaryDetailsUseCase) {
        this.getDailySummaryUseCase = getDailySummaryUseCase;
        this.getHourlySummaryDetailsUseCase = getHourlySummaryDetailsUseCase;
    }

    @Override
    public ResponseEntity<ReportsSummary> getDailySummary(
            @Parameter(description = "Establishment ID")
            Long establishmentId,
            @Parameter(description = "Date in format dd-MM-yy", required = false)
            String dateString) {

        ReportsRequest request = new ReportsRequest(establishmentId, dateString);
        ReportsSummary reportsSummary = getDailySummaryUseCase.execute(request);
        return ResponseEntity.ok(reportsSummary);
    }

    @Override
    public ResponseEntity<DetailedReport> getHourlyDetails(
            @Parameter(description = "Establishment ID")
            Long establishmentId,
            @Parameter(description = "Date in format dd-MM-yy", required = false)
            String dateString) {

        ReportsRequest request = new ReportsRequest(establishmentId, dateString);
        DetailedReport detailedReport = getHourlySummaryDetailsUseCase.execute(request);
        return ResponseEntity.ok(detailedReport);
    }
}
