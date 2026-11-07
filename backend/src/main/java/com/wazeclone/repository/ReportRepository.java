package com.wazeclone.repository;

import com.wazeclone.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findTop10ByOrderByCreatedAtDesc();

    @Query("SELECT r FROM Report r WHERE r.lat BETWEEN :minLat AND :maxLat AND r.lng BETWEEN :minLng AND :maxLng ORDER BY r.createdAt DESC")
    List<Report> findNearby(Double minLat, Double maxLat, Double minLng, Double maxLng);

    // Contagem por tipo
    @Query("SELECT COUNT(r) FROM Report r WHERE r.createdAt BETWEEN :start AND :end AND r.type LIKE :likeType")
    long countByCreatedAtBetweenAndTypeLike(LocalDateTime start, LocalDateTime end, String likeType);

    // Série por hora (0..23)

    @Query("SELECT HOUR(r.createdAt) as hour, COUNT(r) as cnt FROM Report r WHERE r.createdAt BETWEEN :start AND :end GROUP BY HOUR(r.createdAt) ORDER BY hour")
    List<Object[]> countByCreatedAtBetweenGroupedByHourRows(LocalDateTime start, LocalDateTime end);

    default int[] countByCreatedAtBetweenGroupedByHour(LocalDateTime start, LocalDateTime end) {
        int[] arr = new int[24];
        List<Object[]> rows = countByCreatedAtBetweenGroupedByHourRows(start, end);
        for (Object[] row : rows) {
            int hour = ((Number) row[0]).intValue();
            int cnt = ((Number) row[1]).intValue();
            if (hour >= 0 && hour < 24) arr[hour] = cnt;
        }
        return arr;
    }
}


