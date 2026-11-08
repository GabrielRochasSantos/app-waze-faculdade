package com.wazeclone.controller;

import com.wazeclone.model.Route;
import com.wazeclone.repository.RouteRepository;
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
    private final RouteRepository routeRepository;

    public RouteController(RouteService routeService, RouteRepository routeRepository) {
        this.routeService = routeService;
        this.routeRepository = routeRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<Route> save(@RequestBody Route route) {
        return ResponseEntity.ok(routeRepository.save(route));
    }

    @GetMapping("/calculate")
    public ResponseEntity<Route> calculate(@RequestParam String fromAddress,
                                              @RequestParam String toAddress,
                                              @RequestParam String userEmail) {
        Route route = routeService.calculateRoute(fromAddress, toAddress, userEmail);


        return ResponseEntity.ok(route);
    }


    @GetMapping("/history/{userEmail}")
    public ResponseEntity<List<Route>> getHistory(@PathVariable String userEmail) {
        return ResponseEntity.ok(routeRepository.findTop10ByUserEmailOrderByCalculatedAtDesc(userEmail));
    }
}
