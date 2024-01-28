package com.vittorfraga.estacionamentoapi.web;

import com.vittorfraga.estacionamentoapi.reports.DetailedReport;
import com.vittorfraga.estacionamentoapi.reports.ReportsSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/establishments/reports")
@Tag(name = "Reports")
public interface ReportsAPI {

    @GetMapping(value = "/summary", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get daily summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Daily summary retrieved"),
            @ApiResponse(responseCode = "400", description = "Missing parameter"),
            @ApiResponse(responseCode = "404", description = "Establishment not found"),
    })
    ResponseEntity<ReportsSummary> getDailySummary(
            @RequestParam(name = "establishmentId") Long establishmentId,
            @RequestParam(name = "date", required = false) String dateString
    );

    @GetMapping(value = "/detailed", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get hourly details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hourly details retrieved"),
            @ApiResponse(responseCode = "400", description = "Missing parameter"),
            @ApiResponse(responseCode = "404", description = "Establishment not found"),
    })
    ResponseEntity<DetailedReport> getHourlyDetails(
            @RequestParam(name = "establishmentId") Long establishmentId,
            @RequestParam(name = "date", required = false) String dateString
    );
}