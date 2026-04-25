package com.airtel.inventory.service;

import com.airtel.inventory.model.Assignment;
import com.airtel.inventory.model.Device;
import com.airtel.inventory.model.Employee;
import com.airtel.inventory.repository.AssignmentRepository;
import com.airtel.inventory.repository.DeviceRepository;
import com.airtel.inventory.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private DeviceRepository deviceRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Transactional
    public Assignment assignDevice(Long deviceId, Long employeeId, String notes) {
        // Check if device exists and is available
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        if (deviceOpt.isEmpty()) {
            throw new RuntimeException("Device not found");
        }
        
        Device device = deviceOpt.get();
        if (!"Available".equals(device.getStatus())) {
            throw new RuntimeException("Device is not available for assignment");
        }
        
        // Check if employee exists
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            throw new RuntimeException("Employee not found");
        }
        
        // Check if device is already assigned
        Optional<Assignment> existingAssignment = assignmentRepository.findActiveAssignmentByDevice(deviceId);
        if (existingAssignment.isPresent()) {
            throw new RuntimeException("Device is already assigned");
        }
        
        // Create assignment
        Assignment assignment = new Assignment();
        assignment.setDevice(device);
        assignment.setEmployee(employeeOpt.get());
        assignment.setAssignedDate(LocalDate.now());
        assignment.setNotes(notes);
        
        // Update device status
        device.setStatus("Assigned");
        deviceRepository.save(device);
        
        return assignmentRepository.save(assignment);
    }
    
    @Transactional
    public Assignment returnDevice(Long assignmentId) {
        Optional<Assignment> assignmentOpt = assignmentRepository.findById(assignmentId);
        if (assignmentOpt.isEmpty()) {
            throw new RuntimeException("Assignment not found");
        }
        
        Assignment assignment = assignmentOpt.get();
        if (assignment.getReturnDate() != null) {
            throw new RuntimeException("Device has already been returned");
        }
        
        // Update assignment
        assignment.setReturnDate(LocalDate.now());
        
        // Update device status
        Device device = assignment.getDevice();
        device.setStatus("Available");
        deviceRepository.save(device);
        
        return assignmentRepository.save(assignment);
    }
    
    public List<Assignment> findAllAssignments() {
        return assignmentRepository.findAll();
    }
    
    public List<Assignment> findActiveAssignments() {
        return assignmentRepository.findActiveAssignments();
    }
    
    public List<Assignment> findAssignmentsByDevice(Long deviceId) {
        return assignmentRepository.findByDeviceId(deviceId);
    }
    
    public List<Assignment> findAssignmentsByEmployee(Long employeeId) {
        return assignmentRepository.findByEmployeeId(employeeId);
    }
    
    public Optional<Assignment> findById(Long id) {
        return assignmentRepository.findById(id);
    }
    
    public long countActiveAssignments() {
        return assignmentRepository.countActiveAssignments();
    }
    
    @Transactional
    public void deleteAssignment(Long id) {
        Optional<Assignment> assignmentOpt = assignmentRepository.findById(id);
        if (assignmentOpt.isPresent()) {
            Assignment assignment = assignmentOpt.get();
            
            // Update device status back to available if assignment was active
            if (assignment.getReturnDate() == null) {
                Device device = assignment.getDevice();
                device.setStatus("Available");
                deviceRepository.save(device);
            }
            
            assignmentRepository.deleteById(id);
        }
    }
}
