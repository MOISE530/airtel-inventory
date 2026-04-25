package com.airtel.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirtelInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirtelInventoryApplication.class, args);
        System.out.println("============================================================");
        System.out.println("AIRTEL PROFESSIONAL INVENTORY MANAGEMENT SYSTEM STARTED");
        System.out.println("Access URL: http://localhost:8081/airtel-inventory/login");
        System.out.println("Professional Login Credentials:");
        System.out.println("Username: 24RP01839");
        System.out.println("Password: 24RP03971");
        System.out.println("============================================================");
    }
}
