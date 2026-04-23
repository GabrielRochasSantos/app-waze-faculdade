package com.wazeclone.controller;

import com.wazeclone.model.Route;
import com.wazeclone.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "*")
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<Route> calculate(@RequestParam Double fromLat, 
                                          @RequestParam Double fromLng,
                                          @RequestParam Double toLat,
                                          @RequestParam Double toLng,
                                          @RequestParam String userEmail) {
        Route route = routeService.calculateRoute(fromLat, fromLng, toLat, toLng, userEmail);
        return ResponseEntity.ok(route);
    }

    @GetMapping("/history/{userEmail}")
    public ResponseEntity<List<Route>> getHistory(@PathVariable String userEmail) {
        // TODO: Query repo
        return ResponseEntity.ok(List.of());
    }
}
