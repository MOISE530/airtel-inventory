package com.airtel.inventory.service;

import com.airtel.inventory.model.AuditLog;
import com.airtel.inventory.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
public class AuditLogService {
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    
    public AuditLog logActivity(String username, String action, String entityType, String entityId, String details, HttpServletRequest request) {
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        
        AuditLog auditLog = new AuditLog(username, action, entityType, entityId, details, ipAddress, userAgent);
        return auditLogRepository.save(auditLog);
    }
    
    public AuditLog logLogin(String username, HttpServletRequest request) {
        return logActivity(username, "LOGIN", "USER", username, "User logged in", request);
    }
    
    public AuditLog logLogout(String username, HttpServletRequest request) {
        return logActivity(username, "LOGOUT", "USER", username, "User logged out", request);
    }
    
    public AuditLog logDeviceCreate(String username, String deviceId, String deviceDetails, HttpServletRequest request) {
        return logActivity(username, "CREATE", "DEVICE", deviceId, "Created device: " + deviceDetails, request);
    }
    
    public AuditLog logDeviceUpdate(String username, String deviceId, String deviceDetails, HttpServletRequest request) {
        return logActivity(username, "UPDATE", "DEVICE", deviceId, "Updated device: " + deviceDetails, request);
    }
    
    public AuditLog logDeviceDelete(String username, String deviceId, String deviceDetails, HttpServletRequest request) {
        return logActivity(username, "DELETE", "DEVICE", deviceId, "Deleted device: " + deviceDetails, request);
    }
    
    public AuditLog logDeviceAssign(String username, String deviceId, String employeeName, HttpServletRequest request) {
        return logActivity(username, "ASSIGN", "DEVICE", deviceId, "Device assigned to: " + employeeName, request);
    }
    
    public AuditLog logDeviceReturn(String username, String deviceId, String employeeName, HttpServletRequest request) {
        return logActivity(username, "RETURN", "DEVICE", deviceId, "Device returned by: " + employeeName, request);
    }
    
    public AuditLog logEmployeeCreate(String username, String employeeId, String employeeName, HttpServletRequest request) {
        return logActivity(username, "CREATE", "EMPLOYEE", employeeId, "Created employee: " + employeeName, request);
    }
    
    public AuditLog logEmployeeUpdate(String username, String employeeId, String employeeName, HttpServletRequest request) {
        return logActivity(username, "UPDATE", "EMPLOYEE", employeeId, "Updated employee: " + employeeName, request);
    }
    
    public AuditLog logEmployeeDelete(String username, String employeeId, String employeeName, HttpServletRequest request) {
        return logActivity(username, "DELETE", "EMPLOYEE", employeeId, "Deleted employee: " + employeeName, request);
    }
    
    public List<AuditLog> findAll() {
        return auditLogRepository.findAll();
    }
    
    public List<AuditLog> findByUsername(String username) {
        return auditLogRepository.findByUsernameOrderByTimestampDesc(username);
    }
    
    public List<AuditLog> findByEntityType(String entityType) {
        return auditLogRepository.findByEntityTypeOrderByTimestampDesc(entityType);
    }
    
    public List<AuditLog> findByAction(String action) {
        return auditLogRepository.findByActionOrderByTimestampDesc(action);
    }
    
    public List<AuditLog> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return auditLogRepository.findByDateRange(startDate, endDate);
    }
    
    public List<AuditLog> findRecentLogs(int limit) {
        List<AuditLog> allLogs = auditLogRepository.findAll();
        allLogs.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));
        
        List<AuditLog> recentLogs = new java.util.ArrayList<>();
        for (int i = 0; i < Math.min(limit, allLogs.size()); i++) {
            recentLogs.add(allLogs.get(i));
        }
        return recentLogs;
    }
    
    public long countLogsSince(LocalDateTime startDate) {
        return auditLogRepository.countLogsSince(startDate);
    }
    
    public long countLogins() {
        return auditLogRepository.countByAction("LOGIN");
    }
    
    public long countLogouts() {
        return auditLogRepository.countByAction("LOGOUT");
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0];
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}
