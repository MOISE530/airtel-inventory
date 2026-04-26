package com.airtel.inventory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.airtel.inventory.model.User;
import com.airtel.inventory.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("24RP01839").isEmpty()) {
                User user = new User();
                user.setUsername("24RP01839");
                user.setPassword("24RP03971");
                userRepository.save(user);
                System.out.println("✅ Default user created");
            }
        };
    }
}