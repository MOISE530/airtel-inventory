package com.airtel.inventory.service;

import com.airtel.inventory.model.Device;
import com.airtel.inventory.model.Employee;
import com.airtel.inventory.model.Assignment;
import com.airtel.inventory.repository.DeviceRepository;
import com.airtel.inventory.repository.EmployeeRepository;
import com.airtel.inventory.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    
    @Autowired
    private DeviceRepository deviceRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    public Map<String, Object> getDashboardAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        
        // Device Statistics
        analytics.put("totalDevices", deviceRepository.count());
        analytics.put("availableDevices", deviceRepository.countByStatus("Available"));
        analytics.put("assignedDevices", deviceRepository.countByStatus("Assigned"));
        analytics.put("maintenanceDevices", deviceRepository.countByStatus("Maintenance"));
        
        // Employee Statistics
        analytics.put("totalEmployees", employeeRepository.count());
        analytics.put("activeEmployees", employeeRepository.count());
        
        // Assignment Statistics
        analytics.put("totalAssignments", assignmentRepository.count());
        analytics.put("activeAssignments", assignmentRepository.countByReturnDateIsNull());
        
        // Device Type Distribution
        analytics.put("deviceTypeDistribution", getDeviceTypeDistribution());
        
        // Department Distribution
        analytics.put("departmentDistribution", getDepartmentDistribution());
        
        // Monthly Assignment Trends
        analytics.put("monthlyTrends", getMonthlyAssignmentTrends());
        
        // Device Condition Overview
        analytics.put("conditionOverview", getDeviceConditionOverview());
        
        // Recent Activity
        analytics.put("recentActivity", getRecentActivity());
        
        return analytics;
    }
    
    private Map<String, Long> getDeviceTypeDistribution() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream()
                .collect(Collectors.groupingBy(Device::getDeviceType, Collectors.counting()));
    }
    
    private Map<String, Long> getDepartmentDistribution() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
    }
    
    private List<Map<String, Object>> getMonthlyAssignmentTrends() {
        List<Assignment> assignments = assignmentRepository.findAll();
        Map<String, Long> monthlyData = assignments.stream()
                .filter(a -> a.getAssignedDate() != null)
                .collect(Collectors.groupingBy(
                    a -> a.getAssignedDate().format(DateTimeFormatter.ofPattern("MMM yyyy")),
                    Collectors.counting()
                ));
        
        // Get last 6 months
        List<Map<String, Object>> trends = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (int i = 5; i >= 0; i--) {
            LocalDate month = now.minusMonths(i);
            String monthKey = month.format(DateTimeFormatter.ofPattern("MMM yyyy"));
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", monthKey);
            monthData.put("count", monthlyData.getOrDefault(monthKey, 0L));
            trends.add(monthData);
        }
        
        return trends;
    }
    
    private Map<String, Long> getDeviceConditionOverview() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream()
                .collect(Collectors.groupingBy(Device::getDeviceCondition, Collectors.counting()));
    }
    
    private List<Map<String, Object>> getRecentActivity() {
        List<Assignment> assignments = assignmentRepository.findAll();
        return assignments.stream()
                .filter(a -> a.getAssignedDate() != null)
                .sorted((a1, a2) -> a2.getAssignedDate().compareTo(a1.getAssignedDate()))
                .limit(5)
                .map(a -> {
                    Map<String, Object> activity = new HashMap<>();
                    activity.put("type", "assignment");
                    activity.put("deviceName", a.getDevice().getAssetTag());
                    activity.put("employeeName", a.getEmployee().getFullName());
                    activity.put("date", a.getAssignedDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
                    activity.put("status", a.getReturnDate() == null ? "Active" : "Returned");
                    return activity;
                })
                .collect(Collectors.toList());
    }
    
    public Map<String, Object> getDeviceAnalytics(Long deviceId) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        if (deviceOpt.isEmpty()) {
            return Collections.emptyMap();
        }
        
        Device device = deviceOpt.get();
        Map<String, Object> analytics = new HashMap<>();
        
        analytics.put("device", device);
        analytics.put("totalAssignments", assignmentRepository.countByDevice(device));
        analytics.put("currentAssignment", assignmentRepository.findByDeviceAndReturnDateIsNull(device));
        analytics.put("assignmentHistory", assignmentRepository.findByDeviceOrderByAssignedDateDesc(device));
        
        return analytics;
    }
    
    public Map<String, Object> getEmployeeAnalytics(Long employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            return Collections.emptyMap();
        }
        
        Employee employee = employeeOpt.get();
        Map<String, Object> analytics = new HashMap<>();
        
        analytics.put("employee", employee);
        analytics.put("totalAssignments", assignmentRepository.countByEmployee(employee));
        analytics.put("activeAssignments", assignmentRepository.findByEmployeeAndReturnDateIsNull(employee));
        analytics.put("assignmentHistory", assignmentRepository.findByEmployeeOrderByAssignedDateDesc(employee));
        
        return analytics;
    }
}
