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
            // authService valida senha e gera token
            String token = authService.login(email, senha);
            // Para exibir nome no frontend
            String nome = authService.getNomeByEmail(email);
            return ResponseEntity.ok(Map.of("token", token, "message", "Login OK!", "nome", nome));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody Map<String, String> body) {
        try {
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new RuntimeException("Token ausente ou inválido");
            }
            authService.changePassword(authorization.substring(7), body.get("novaSenha"));
            return ResponseEntity.ok(Map.of("message", "Senha alterada com sucesso!"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/update-profile")
    public ResponseEntity<Map<String, String>> updateProfile(@RequestBody Map<String, String> update) {
        // TODO: Implement with JWT validation
        return ResponseEntity.ok(Map.of("message", "Perfil atualizado"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String code = authService.forgotPassword(email);
            // Para teste: devolve código no JSON (sem email real)
            return ResponseEntity.ok(Map.of("message", "Código gerado!", "resetCode", code));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String resetCode = body.get("resetCode");
            String novaSenha = body.get("novaSenha");
            authService.resetPassword(email, resetCode, novaSenha);
            return ResponseEntity.ok(Map.of("message", "Senha redefinida com sucesso!"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}

