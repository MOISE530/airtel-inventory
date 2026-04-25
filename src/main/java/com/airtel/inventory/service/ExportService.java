package com.airtel.inventory.service;

import com.airtel.inventory.model.Device;
import com.airtel.inventory.model.Employee;
import com.airtel.inventory.model.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExportService {
    
    @Autowired
    private DeviceService deviceService;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private AssignmentService assignmentService;
    
    // Export devices to CSV format
    public ByteArrayOutputStream exportDevicesToCSV() throws IOException {
        List<Device> devices = deviceService.findAll();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        // CSV Header
        String csvHeader = "ID,Asset Tag,Serial Number,Device Type,Brand,Model,Specifications,Condition,Status,Purchase Date,Warranty Until\n";
        outputStream.write(csvHeader.getBytes());
        
        // CSV Data
        for (Device device : devices) {
            String csvRow = String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                device.getId(),
                escapeCsv(device.getAssetTag()),
                escapeCsv(device.getSerialNumber()),
                escapeCsv(device.getDeviceType()),
                escapeCsv(device.getBrand()),
                escapeCsv(device.getModel()),
                escapeCsv(device.getSpecifications()),
                escapeCsv(device.getDeviceCondition()),
                escapeCsv(device.getStatus()),
                device.getPurchaseDate() != null ? device.getPurchaseDate().toString() : "",
                device.getWarrantyUntil() != null ? device.getWarrantyUntil().toString() : ""
            );
            outputStream.write(csvRow.getBytes());
        }
        
        return outputStream;
    }
    
    // Export employees to CSV format
    public ByteArrayOutputStream exportEmployeesToCSV() throws IOException {
        List<Employee> employees = employeeService.findAll();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        // CSV Header
        String csvHeader = "ID,Employee ID,Full Name,Department,Designation,Email,Phone\n";
        outputStream.write(csvHeader.getBytes());
        
        // CSV Data
        for (Employee employee : employees) {
            String csvRow = String.format("%d,%s,%s,%s,%s,%s,%s\n",
                employee.getId(),
                escapeCsv(employee.getEmployeeId()),
                escapeCsv(employee.getFullName()),
                escapeCsv(employee.getDepartment()),
                escapeCsv(employee.getDesignation()),
                escapeCsv(employee.getEmail()),
                escapeCsv(employee.getPhone())
            );
            outputStream.write(csvRow.getBytes());
        }
        
        return outputStream;
    }
    
    // Export assignments to CSV format
    public ByteArrayOutputStream exportAssignmentsToCSV() throws IOException {
        List<Assignment> assignments = assignmentService.findAllAssignments();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        // CSV Header
        String csvHeader = "ID,Device Asset Tag,Device Brand,Device Model,Employee Name,Employee ID,Department,Assigned Date,Return Date,Status,Notes\n";
        outputStream.write(csvHeader.getBytes());
        
        // CSV Data
        for (Assignment assignment : assignments) {
            String status = assignment.getReturnDate() != null ? "Returned" : "Active";
            String csvRow = String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                assignment.getId(),
                escapeCsv(assignment.getDevice().getAssetTag()),
                escapeCsv(assignment.getDevice().getBrand()),
                escapeCsv(assignment.getDevice().getModel()),
                escapeCsv(assignment.getEmployee().getFullName()),
                escapeCsv(assignment.getEmployee().getEmployeeId()),
                escapeCsv(assignment.getEmployee().getDepartment()),
                assignment.getAssignedDate().toString(),
                assignment.getReturnDate() != null ? assignment.getReturnDate().toString() : "",
                status,
                escapeCsv(assignment.getNotes())
            );
            outputStream.write(csvRow.getBytes());
        }
        
        return outputStream;
    }
    
    // Generate inventory summary report
    public ByteArrayOutputStream generateInventorySummary() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        // Report Header
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String reportHeader = String.format("AIRTEL PROFESSIONAL INVENTORY MANAGEMENT SYSTEM\nINVENTORY SUMMARY REPORT\nGenerated on: %s\n\n", timestamp);
        outputStream.write(reportHeader.getBytes());
        
        // Device Statistics
        long totalDevices = deviceService.count();
        long availableDevices = deviceService.countAvailable();
        long assignedDevices = deviceService.countAssigned();
        long maintenanceDevices = deviceService.countMaintenance();
        long lostDevices = deviceService.countLost();
        
        String deviceStats = String.format("DEVICE STATISTICS\n================\nTotal Devices: %d\nAvailable Devices: %d\nAssigned Devices: %d\nDevices in Maintenance: %d\nLost Devices: %d\n\n", 
            totalDevices, availableDevices, assignedDevices, maintenanceDevices, lostDevices);
        outputStream.write(deviceStats.getBytes());
        
        // Employee Statistics
        long totalEmployees = employeeService.count();
        String employeeStats = String.format("EMPLOYEE STATISTICS\n===================\nTotal Employees: %d\n\n", totalEmployees);
        outputStream.write(employeeStats.getBytes());
        
        // Assignment Statistics
        long totalAssignments = assignmentService.countActiveAssignments();
        String assignmentStats = String.format("ASSIGNMENT STATISTICS\n=====================\nActive Assignments: %d\n\n", totalAssignments);
        outputStream.write(assignmentStats.getBytes());
        
        // Device Type Breakdown
        String deviceTypeBreakdown = "DEVICE TYPE BREAKDOWN\n=====================\n";
        outputStream.write(deviceTypeBreakdown.getBytes());
        
        String[] deviceTypes = {"Laptop", "Desktop", "Mobile", "Tablet", "Monitor"};
        for (String deviceType : deviceTypes) {
            long count = deviceService.countByDeviceType(deviceType);
            if (count > 0) {
                String typeLine = String.format("%s: %d\n", deviceType, count);
                outputStream.write(typeLine.getBytes());
            }
        }
        
        return outputStream;
    }
    
    // Helper method to escape CSV values
    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
    
    // Generate professional filename
    public String generateFilename(String reportType, String format) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return String.format("airtel_inventory_%s_%s.%s", reportType.toLowerCase(), timestamp, format.toLowerCase());
    }
}
