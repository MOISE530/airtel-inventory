package com.airtel.inventory.repository;

import com.airtel.inventory.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    List<AuditLog> findByUsernameOrderByTimestampDesc(String username);
    
    List<AuditLog> findByEntityTypeOrderByTimestampDesc(String entityType);
    
    List<AuditLog> findByActionOrderByTimestampDesc(String action);
    
    @Query("SELECT a FROM AuditLog a WHERE a.timestamp >= :startDate AND a.timestamp <= :endDate ORDER BY a.timestamp DESC")
    List<AuditLog> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT a FROM AuditLog a WHERE a.username = :username AND a.timestamp >= :startDate AND a.timestamp <= :endDate ORDER BY a.timestamp DESC")
    List<AuditLog> findByUsernameAndDateRange(@Param("username") String username, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT a FROM AuditLog a WHERE a.entityType = :entityType AND a.action = :action ORDER BY a.timestamp DESC")
    List<AuditLog> findByEntityTypeAndAction(@Param("entityType") String entityType, @Param("action") String action);
    
    @Query("SELECT COUNT(a) FROM AuditLog a WHERE a.timestamp >= :startDate")
    long countLogsSince(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT COUNT(a) FROM AuditLog a WHERE a.action = :action")
    long countByAction(@Param("action") String action);
}
