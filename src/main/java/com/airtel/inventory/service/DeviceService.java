package com.airtel.inventory.service;

import com.airtel.inventory.model.Device;
import com.airtel.inventory.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    
    @Autowired
    private DeviceRepository deviceRepository;
    
    public Device save(Device device) {
        if (device.getStatus() == null) {
            device.setStatus("Available");
        }
        if (device.getDeviceCondition() == null) {
            device.setDeviceCondition("Good");
        }
        return deviceRepository.save(device);
    }
    
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }
    
    public Optional<Device> findById(Long id) {
        return deviceRepository.findById(id);
    }
    
    public void deleteById(Long id) {
        deviceRepository.deleteById(id);
    }
    
    public long count() {
        return deviceRepository.count();
    }
    
    public long countAvailable() {
        return deviceRepository.findByStatus("Available").size();
    }
    
    // Advanced search methods
    public List<Device> searchDevices(String keyword) {
        return deviceRepository.searchDevices(keyword);
    }
    
    public List<Device> filterDevices(String deviceType, String brand, String status, String condition) {
        return deviceRepository.filterDevices(deviceType, brand, status, condition);
    }
    
    public List<Device> findByAssetTagContaining(String assetTag) {
        return deviceRepository.findByAssetTagContaining(assetTag);
    }
    
    public List<Device> findByBrand(String brand) {
        return deviceRepository.findByBrand(brand);
    }
    
    public List<Device> findByDeviceType(String deviceType) {
        return deviceRepository.findByDeviceType(deviceType);
    }
    
    public long countByStatus(String status) {
        return deviceRepository.countByStatus(status);
    }
    
    public long countByDeviceType(String deviceType) {
        return deviceRepository.countByDeviceType(deviceType);
    }
    
    // Professional statistics methods
    public long countAssigned() {
        return deviceRepository.countByStatus("Assigned");
    }
    
    public long countMaintenance() {
        return deviceRepository.countByStatus("Maintenance");
    }
    
    public long countLost() {
        return deviceRepository.countByStatus("Lost");
    }
    
    public List<Device> findAvailableDevices() {
        return deviceRepository.findByStatus("Available");
    }
    
    public List<Device> findAssignedDevices() {
        return deviceRepository.findByStatus("Assigned");
    }
}
