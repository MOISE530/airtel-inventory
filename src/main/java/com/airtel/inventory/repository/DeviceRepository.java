package com.airtel.inventory.repository;

import com.airtel.inventory.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    
    List<Device> findByStatus(String status);
    
    @Query("SELECT d FROM Device d WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(d.assetTag) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(d.serialNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(d.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(d.model) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(d.specifications) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Device> searchDevices(@Param("keyword") String keyword);
    
    @Query("SELECT d FROM Device d WHERE " +
           "(:deviceType IS NULL OR d.deviceType = :deviceType) AND " +
           "(:brand IS NULL OR :brand = '' OR d.brand = :brand) AND " +
           "(:status IS NULL OR :status = '' OR d.status = :status) AND " +
           "(:condition IS NULL OR :condition = '' OR d.deviceCondition = :condition)")
    List<Device> filterDevices(
        @Param("deviceType") String deviceType,
        @Param("brand") String brand,
        @Param("status") String status,
        @Param("condition") String condition
    );
    
    @Query("SELECT d FROM Device d WHERE " +
           "LOWER(d.assetTag) LIKE LOWER(CONCAT('%', :assetTag, '%'))")
    List<Device> findByAssetTagContaining(@Param("assetTag") String assetTag);
    
    @Query("SELECT d FROM Device d WHERE " +
           "LOWER(d.brand) = LOWER(:brand)")
    List<Device> findByBrand(@Param("brand") String brand);
    
    @Query("SELECT d FROM Device d WHERE " +
           "d.deviceType = :deviceType")
    List<Device> findByDeviceType(@Param("deviceType") String deviceType);
    
    @Query("SELECT COUNT(d) FROM Device d WHERE d.status = :status")
    long countByStatus(@Param("status") String status);
    
    @Query("SELECT COUNT(d) FROM Device d WHERE d.deviceType = :deviceType")
    long countByDeviceType(@Param("deviceType") String deviceType);
}
