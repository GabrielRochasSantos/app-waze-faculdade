package com.wazeclone.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double fromLat, fromLng;
    private Double toLat, toLng;

    private Double distanceKm;  // km
    private Integer estimatedTimeMin;  // min

    private Integer trafficScore;  // 0-100 (0=pesado)

    private String polyline;  // GeoJSON line para Leaflet

    private String type;  // \"rápida\", \"sem_trânsito\", \"curta\"

    private LocalDateTime calculatedAt = LocalDateTime.now();

    private String userEmail;

    public Route() {}

    public Route(Double fromLat, Double fromLng, Double toLat, Double toLng, String userEmail) {
        this.fromLat = fromLat;
        this.fromLng = fromLng;
        this.toLat = toLat;
        this.toLng = toLng;
        this.userEmail = userEmail;
    }

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getFromLat() { return fromLat; }
    public void setFromLat(Double fromLat) { this.fromLat = fromLat; }

    public Double getFromLng() { return fromLng; }
    public void setFromLng(Double fromLng) { this.fromLng = fromLng; }

    public Double getToLat() { return toLat; }
    public void setToLat(Double toLat) { this.toLat = toLat; }

    public Double getToLng() { return toLng; }
    public void setToLng(Double toLng) { this.toLng = toLng; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Integer getEstimatedTimeMin() { return estimatedTimeMin; }
    public void setEstimatedTimeMin(Integer estimatedTimeMin) { this.estimatedTimeMin = estimatedTimeMin; }

    public Integer getTrafficScore() { return trafficScore; }
    public void setTrafficScore(Integer trafficScore) { this.trafficScore = trafficScore; }

    public String getPolyline() { return polyline; }
    public void setPolyline(String polyline) { this.polyline = polyline; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDateTime getCalculatedAt() { return calculatedAt; }
    public void setCalculatedAt(LocalDateTime calculatedAt) { this.calculatedAt = calculatedAt; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
