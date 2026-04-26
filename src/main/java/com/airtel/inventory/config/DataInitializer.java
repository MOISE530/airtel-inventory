package com.airtel.inventory.config;

import com.airtel.inventory.model.User;
import com.airtel.inventory.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if default admin user exists
        if (!userRepository.existsByUsername("24RP01839")) {
            // Create default admin user
            User adminUser = new User();
            adminUser.setUsername("24RP01839");
            adminUser.setPassword("24RP03971"); // In production, this should be encoded
            adminUser.setRole("ADMIN");
            adminUser.setEmail("admin@airtel.com");
            adminUser.setFullName("System Administrator");
            
            userRepository.save(adminUser);
            System.out.println("Default admin user created successfully!");
        }
    }
}
