package com.wazeclone.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wazeclone.model.Route;
import com.wazeclone.repository.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final WebClient webClient = WebClient.create();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Route calculateRoute(Double fromLat, Double fromLng, Double toLat, Double toLng, String userEmail) {
        String url = String.format("https://graphhopper.com/api/1/route?point=%.6f,%.6f&point=%.6f,%.6f&vehicle=car&calc_points=true&debug=true", 
                                   fromLat, fromLng, toLat, toLng);
        
        try {
            String response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Parse simple (distance, time)
            JsonNode json = objectMapper.readTree(response);
            JsonNode path = json.path("paths").get(0);
            Double distKm = path.path("distance").asDouble() / 1000;
            Double timeSec = path.path("time").asDouble() / 60;
            Integer trafficScore = (int) (Math.random() * 40 + 10); // Simula trânsito

            Route route = new Route(fromLat, fromLng, toLat, toLng, userEmail);
            route.setDistanceKm(distKm);
            route.setEstimatedTimeMin(timeSec.intValue());
            route.setTrafficScore(trafficScore);
            route.setType("rápida");
            route.setPolyline(path.path("points").path("encoded_polyline").asText(""));

            routeRepository.save(route);
            return route;

        } catch (Exception e) {
            // Fallback demo rota
            Route demo = new Route(fromLat, fromLng, toLat, toLng, userEmail);
            demo.setDistanceKm(5.2);
            demo.setEstimatedTimeMin(12);
            demo.setTrafficScore(25);
            demo.setType("demo_rápida");
            demo.setPolyline("");
            return routeRepository.save(demo);
        }
    }
}
