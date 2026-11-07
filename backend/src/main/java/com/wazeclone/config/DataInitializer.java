package com.wazeclone.config;

import com.wazeclone.model.User;
import com.wazeclone.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedDemoUser(UserRepository userRepository) {
        return args -> {
            String email = "italo@test.com";
            if (userRepository.findByEmail(email).isEmpty()) {
                User demo = new User("Italo", email, new BCryptPasswordEncoder().encode("123456"));
                userRepository.save(demo);
            }
        };
    }
}
