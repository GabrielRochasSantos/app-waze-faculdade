package com.wazeclone.service;

import com.wazeclone.model.User;
import com.wazeclone.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final SecretKey secretKey;

    private final EmailService emailService;

    public AuthService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.secretKey = Keys.hmacShaKeyFor("italoNavSecretKeySuperSegura2024".getBytes());
    }



    public String register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        user.setSenha(encoder.encode(user.getSenha()));
        userRepository.save(user);
        return generateToken(user.getEmail());
    }

    public String login(String email, String senha) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty() || !encoder.matches(senha, userOpt.get().getSenha())) {
            throw new RuntimeException("Email ou senha incorretos");
        }
        return generateToken(email);
    }

    private String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
                .signWith(secretKey)
                .compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getNomeByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getNome)
                .orElseThrow(() -> new RuntimeException("Email não cadastrado"));
    }

    public void changePassword(String token, String novaSenha) {
        if (novaSenha == null || novaSenha.length() < 6) {
            throw new RuntimeException("Senha deve ter no mínimo 6 caracteres");
        }
        String email = getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não cadastrado"));
        user.setSenha(encoder.encode(novaSenha));
        userRepository.save(user);
    }

    public String forgotPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não cadastrado"));

        String code = String.format("%06d", (int) (Math.random() * 1_000_000));
        user.setPasswordResetCode(code);
        user.setPasswordResetExpiresAt(java.time.LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);

        // Envia por email real (SMTP)
        emailService.sendResetPasswordEmail(email, code);

        // Continua retornando o código só para manter o frontend atual funcionando
        return code;
    }


    public void resetPassword(String email, String resetCode, String newSenha) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não cadastrado"));

        if (user.getPasswordResetCode() == null || user.getPasswordResetExpiresAt() == null) {
            throw new RuntimeException("Solicite um novo código de redefinição de senha");
        }

        if (!user.getPasswordResetCode().equals(resetCode)) {
            throw new RuntimeException("Código inválido" );
        }

        if (java.time.LocalDateTime.now().isAfter(user.getPasswordResetExpiresAt())) {
            throw new RuntimeException("Código expirado" );
        }

        if (newSenha == null || newSenha.length() < 6) {
            throw new RuntimeException("Senha deve ter no mínimo 6 caracteres" );
        }

        user.setSenha(encoder.encode(newSenha));
        user.setPasswordResetCode(null);
        user.setPasswordResetExpiresAt(null);
        userRepository.save(user);
    }
}

