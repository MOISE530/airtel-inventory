package com.airtel.inventory.config;

import com.airtel.inventory.model.User;
import com.airtel.inventory.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("=== DATA INITIALIZER STARTED ===");
        
        try {
            // Check if default admin user exists
            boolean userExists = userRepository.existsByUsername("24RP01839");
            System.out.println("Default admin user exists: " + userExists);
            
            if (!userExists) {
                // Create default admin user
                User adminUser = new User();
                adminUser.setUsername("24RP01839");
                adminUser.setPassword("24RP03971"); // In production, this should be encoded
                adminUser.setRole("ADMIN");
                adminUser.setEmail("admin@airtel.com");
                adminUser.setFullName("System Administrator");
                
                User savedUser = userRepository.save(adminUser);
                System.out.println("Default admin user created successfully! ID: " + savedUser.getId());
                System.out.println("Username: " + savedUser.getUsername());
                System.out.println("Password: " + savedUser.getPassword());
            } else {
                System.out.println("Default admin user already exists.");
            }
        } catch (Exception e) {
            System.err.println("Error creating default admin user: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== DATA INITIALIZER COMPLETED ===");
    }
}
