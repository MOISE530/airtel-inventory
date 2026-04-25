package com.airtel.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "devices")
public class Device {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String assetTag;
    private String serialNumber;
    private String deviceType;
    private String brand;
    private String model;
    private String specifications;
    private String deviceCondition;
    private String status;
    private LocalDate purchaseDate;
    private LocalDate warrantyUntil;
    
    public Device() {}
    
    public Device(String assetTag, String serialNumber, String deviceType, String brand, 
                  String model, String specifications) {
        this.assetTag = assetTag;
        this.serialNumber = serialNumber;
        this.deviceType = deviceType;
        this.brand = brand;
        this.model = model;
        this.specifications = specifications;
        this.deviceCondition = "Good";
        this.status = "Available";
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAssetTag() {
        return assetTag;
    }
    
    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
    }
    
    public String getSerialNumber() {
        return serialNumber;
    }
    
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    public String getDeviceType() {
        return deviceType;
    }
    
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getSpecifications() {
        return specifications;
    }
    
    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
    
    public String getDeviceCondition() {
        return deviceCondition;
    }
    
    public void setDeviceCondition(String deviceCondition) {
        this.deviceCondition = deviceCondition;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
    
    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    
    public LocalDate getWarrantyUntil() {
        return warrantyUntil;
    }
    
    public void setWarrantyUntil(LocalDate warrantyUntil) {
        this.warrantyUntil = warrantyUntil;
    }
}
