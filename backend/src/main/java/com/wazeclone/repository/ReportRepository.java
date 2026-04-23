package com.wazeclone.repository;

import com.wazeclone.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    
    List<Report> findTop10ByOrderByCreatedAtDesc();
    
    @Query("SELECT r FROM Report r WHERE r.lat BETWEEN :minLat AND :maxLat AND r.lng BETWEEN :minLng AND :maxLng ORDER BY r.createdAt DESC")
    List<Report> findNearby(Double minLat, Double maxLat, Double minLng, Double maxLng);
}

