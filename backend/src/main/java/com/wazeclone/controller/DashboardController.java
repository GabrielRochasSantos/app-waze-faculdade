package com.wazeclone.controller;

import com.wazeclone.model.Report;
import com.wazeclone.repository.ReportRepository;
import com.wazeclone.repository.UserRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public DashboardController(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        // contagens por tipo (BURACO, TRANSITO, OBRA, RADAR)
        // Obs: o Report tem campo 'type', mas no modelo hoje default é BURACO.
        // Vamos contar por prefixo/palavra simples para aceitar variações.
        long buracosL = reportRepository.countByCreatedAtBetweenAndTypeLike(start, end, "BURACO%");
        long acidentesL = reportRepository.countByCreatedAtBetweenAndTypeLike(start, end, "ACIDENTE%");
        long obrasL = reportRepository.countByCreatedAtBetweenAndTypeLike(start, end, "OBRA%");
        long radaresL = reportRepository.countByCreatedAtBetweenAndTypeLike(start, end, "RADAR%");

        int buracos = buracosL > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) buracosL;
        int acidentes = acidentesL > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) acidentesL;
        int obras = obrasL > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) obrasL;
        int radares = radaresL > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) radaresL;


        int total = buracos + acidentes + obras + radares;

        // usuários ativos (regra simples: existem no banco)
        long totalUsuariosAtivos = userRepository.count();

        Map<String, Object> res = new HashMap<>();
        res.put("buracos", buracos);
        res.put("acidentes", acidentes);
        res.put("obras", obras);
        res.put("radares", radares);
        res.put("totalHoje", total);
        res.put("usuariosAtivos", totalUsuariosAtivos);
        return res;
    }

    @GetMapping("/recent")
    public List<Map<String, Object>> recent() {
        // pega últimos 10
        List<Report> last = reportRepository.findTop10ByOrderByCreatedAtDesc();
        List<Map<String, Object>> out = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

        for (Report r : last) {
            Map<String, Object> row = new HashMap<>();
            String type = normalizeType(r.getType());

            row.put("type", type);
            row.put("endereco", r.getLat() + ", " + r.getLng()); // fallback (sem reverse geocode)
            row.put("hora", r.getCreatedAt() != null ? r.getCreatedAt().format(fmt) : null);
            row.put("status", "ATIVO");

            out.add(row);
        }
        return out;
    }

    @GetMapping("/by-hour")
    public Map<String, Object> byHour() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        int[] counts = reportRepository.countByCreatedAtBetweenGroupedByHour(start, end);


        Map<String, Object> res = new HashMap<>();
        List<Integer> series = new ArrayList<>();
        for (int c : counts) series.add(c);
        res.put("hours", seriesToHours());
        res.put("counts", series);
        return res;
    }

    private List<String> seriesToHours() {
        List<String> hours = new ArrayList<>();
        for (int h = 0; h < 24; h++) {
            hours.add(String.format(Locale.US, "%02d:00", h));
        }
        return hours;
    }

    private String normalizeType(String type) {
        if (type == null) return "BURACO";
        String t = type.toUpperCase(Locale.ROOT);
        if (t.contains("BURACO")) return "BURACO";
        if (t.contains("ACIDENT")) return "ACIDENTE";
        if (t.contains("OBRA")) return "OBRA";
        if (t.contains("RADAR")) return "RADAR";
        if (t.contains("TRANSITO")) return "TRANSITO";
        return t;
    }
}

