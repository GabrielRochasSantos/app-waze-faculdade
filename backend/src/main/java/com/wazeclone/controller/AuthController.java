package com.wazeclone.controller;

import com.wazeclone.model.User;
import com.wazeclone.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        try {
            String token = authService.register(user);
            return ResponseEntity.ok(Map.of("token", token, "message", "Usuário criado!", "userId", String.valueOf(user.getId())));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String senha = credentials.get("senha");
            String token = authService.login(email, senha);
            return ResponseEntity.ok(Map.of("token", token, "message", "Login OK!"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/update-profile")
    public ResponseEntity<Map<String, String>> updateProfile(@RequestBody Map<String, String> update) {
        // TODO: Implement with JWT validation
        return ResponseEntity.ok(Map.of("message", "Perfil atualizado"));
    }
}
