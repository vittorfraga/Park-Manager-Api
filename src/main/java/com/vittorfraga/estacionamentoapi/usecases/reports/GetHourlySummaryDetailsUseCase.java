package com.vittorfraga.estacionamentoapi.usecases.reports;

import com.vittorfraga.estacionamentoapi.domain.parkingaccess.VehicleEventType;
import com.vittorfraga.estacionamentoapi.reports.DetailedReport;
import com.vittorfraga.estacionamentoapi.reports.HourlyCount;
import com.vittorfraga.estacionamentoapi.reports.ReportsData;
import com.vittorfraga.estacionamentoapi.reports.ReportsQueryRepository;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import com.vittorfraga.estacionamentoapi.usecases.reports.dto.ReportsRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


@Component
public class GetHourlySummaryDetailsUseCase extends UseCase<ReportsRequest, DetailedReport> {

    private final ReportsQueryRepository reportsQueryRepository;

    public GetHourlySummaryDetailsUseCase(ReportsQueryRepository reportsQueryRepository) {
        this.reportsQueryRepository = reportsQueryRepository;
    }

    @Override
    public DetailedReport execute(ReportsRequest request) {
        LocalDate day = getDay(request.dateString());

       
        List<HourlyCount> entryCountsPerHour = reportsQueryRepository.findCountsPerHour(request.establishmentId(), day, VehicleEventType.ENTRY);
        Map<Integer, Integer> entriesPerHour = convertHourlyCountListToHourCountMap(entryCountsPerHour);


        List<HourlyCount> exitCountsPerHour = reportsQueryRepository.findCountsPerHour(request.establishmentId(), day, VehicleEventType.EXIT);
        Map<Integer, Integer> exitsPerHour = convertHourlyCountListToHourCountMap(exitCountsPerHour);

        String description = "Listing only hours with entry or exit";
        String hourFormat = "HH";
        String formattedDate = day.format(DateTimeFormatter.ofPattern("dd/MM/yy"));

        ReportsData reportsData = new ReportsData(entriesPerHour, exitsPerHour);
        return new DetailedReport(description, hourFormat, formattedDate, reportsData);
    }

    private static Map<Integer, Integer> convertHourlyCountListToHourCountMap(List<HourlyCount> exitCountsPerHour) {
        return exitCountsPerHour.stream()
                .collect(Collectors.toMap(
                        HourlyCount::getHour,
                        HourlyCount::getCount,
                        (existing, replacement) -> existing,
                        TreeMap::new
                ));
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
