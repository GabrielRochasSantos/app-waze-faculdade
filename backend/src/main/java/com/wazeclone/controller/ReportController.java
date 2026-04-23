package com.wazeclone.controller;

import com.wazeclone.model.Report;
import com.wazeclone.repository.ReportRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:8080") // Frontend live-server
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    @GetMapping
    public List<Report> getAllRecent() {
        return reportRepository.findTop10ByOrderByCreatedAtDesc();
    }

    @PostMapping
    public ResponseEntity<Report> createReport(@Valid @RequestBody Report report) {
        Report saved = reportRepository.save(report);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/nearby")
    public List<Report> getNearby(@RequestParam Double lat, @RequestParam Double lng, 
                                  @RequestParam(defaultValue = "0.01") Double distance) {
        Double minLat = lat - distance;
        Double maxLat = lat + distance;
        Double minLng = lng - distance;
        Double maxLng = lng + distance;
        return reportRepository.findNearby(minLat, maxLat, minLng, maxLng);
    }
}
