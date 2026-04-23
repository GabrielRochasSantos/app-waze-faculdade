package com.wazeclone.repository;

import com.wazeclone.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findTop10ByUserEmailOrderByCalculatedAtDesc(String userEmail);
}
