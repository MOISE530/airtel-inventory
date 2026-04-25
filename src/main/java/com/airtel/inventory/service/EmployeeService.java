package com.airtel.inventory.service;

import com.airtel.inventory.model.Employee;
import com.airtel.inventory.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }
    
    public Optional<Employee> findByEmployeeId(String employeeId) {
        return employeeRepository.findByEmployeeId(employeeId);
    }
    
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
    
    public long count() {
        return employeeRepository.count();
    }
    
    public boolean existsByEmployeeId(String employeeId) {
        return findByEmployeeId(employeeId).isPresent();
    }
    
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isEmpty()) {
            throw new RuntimeException("Employee not found");
        }
        
        Employee employee = employeeOpt.get();
        employee.setFullName(employeeDetails.getFullName());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setDesignation(employeeDetails.getDesignation());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhone(employeeDetails.getPhone());
        
        return employeeRepository.save(employee);
    }
}
