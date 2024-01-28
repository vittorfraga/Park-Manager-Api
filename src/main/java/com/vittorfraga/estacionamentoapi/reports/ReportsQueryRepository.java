package com.vittorfraga.estacionamentoapi.reports;

import com.vittorfraga.estacionamentoapi.domain.parkingaccess.VehicleEventType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ReportsQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<HourlyCount> findCountsPerHour(Long establishmentId, LocalDate day, VehicleEventType eventType) {
        String queryString = "SELECT NEW com.vittorfraga.estacionamentoapi.reports.HourlyCount(HOUR(eac.createdAt), COUNT(eac)) " +
                "FROM EstablishmentAccessControl eac " +
                "WHERE eac.establishment.id = :establishmentId " +
                "AND eac.eventType = :eventType " +
                "AND DATE(eac.createdAt) = :day " +
                "GROUP BY HOUR(eac.createdAt)";

        TypedQuery<HourlyCount> query = entityManager.createQuery(queryString, HourlyCount.class);
        query.setParameter("establishmentId", establishmentId);
        query.setParameter("eventType", eventType);
        query.setParameter("day", day);

        return query.getResultList();
    }

    public Integer countByEventTypeAndDay(Long establishmentId, LocalDate day, VehicleEventType eventType) {
        String queryString = "SELECT COUNT(eac) FROM EstablishmentAccessControl eac " +
                "WHERE eac.establishment.id = :establishmentId " +
                "AND eac.eventType = :eventType " +
                "AND DATE(eac.createdAt) = :day";

        TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("establishmentId", establishmentId);
        query.setParameter("eventType", eventType);
        query.setParameter("day", day);

        return Math.toIntExact(query.getSingleResult());
    }
}
