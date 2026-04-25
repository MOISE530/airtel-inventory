package com.airtel.inventory.controller;

import com.airtel.inventory.model.Assignment;
import com.airtel.inventory.model.Device;
import com.airtel.inventory.model.Employee;
import com.airtel.inventory.service.AssignmentService;
import com.airtel.inventory.service.DeviceService;
import com.airtel.inventory.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/assignments")
public class AssignmentController {
    
    @Autowired
    private AssignmentService assignmentService;
    
    @Autowired
    private DeviceService deviceService;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @GetMapping("/")
    public String listAssignments(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        List<Assignment> assignments = assignmentService.findAllAssignments();
        model.addAttribute("assignments", assignments);
        model.addAttribute("totalAssignments", assignmentService.countActiveAssignments());
        model.addAttribute("showActiveOnly", false);
        
        return "assignments/list";
    }
    
    @GetMapping("")
    public String listAssignmentsNoSlash(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        List<Assignment> assignments = assignmentService.findAllAssignments();
        model.addAttribute("assignments", assignments);
        model.addAttribute("activeAssignmentsCount", assignmentService.countActiveAssignments());
        model.addAttribute("showActiveOnly", false);
        
        return "assignments/list";
    }
    
    @GetMapping("/active")
    public String listActiveAssignments(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        List<Assignment> assignments = assignmentService.findActiveAssignments();
        model.addAttribute("assignments", assignments);
        model.addAttribute("activeAssignmentsCount", assignmentService.countActiveAssignments());
        model.addAttribute("showActiveOnly", true);
        
        return "assignments/list";
    }
    
    @GetMapping("/assign")
    public String showAssignForm(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        // Get available devices
        List<Device> allDevices = deviceService.findAll();
        List<Device> availableDevices = new java.util.ArrayList<>();
        for (Device device : allDevices) {
            if ("Available".equals(device.getStatus())) {
                availableDevices.add(device);
            }
        }
        
        // Get all employees
        List<Employee> employees = employeeRepository.findAll();
        
        model.addAttribute("availableDevices", availableDevices);
        model.addAttribute("employees", employees);
        model.addAttribute("assignment", new Assignment());
        
        return "assignments/assign";
    }
    
    @PostMapping("/assign")
    public String assignDevice(@RequestParam Long deviceId,
                              @RequestParam Long employeeId,
                              @RequestParam(required = false) String notes,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        try {
            Assignment assignment = assignmentService.assignDevice(deviceId, employeeId, notes);
            redirectAttributes.addFlashAttribute("success", "Device assigned successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error assigning device: " + e.getMessage());
        }
        
        return "redirect:/assignments";
    }
    
    @GetMapping("/return/{id}")
    public String returnDevice(@PathVariable Long id,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        try {
            Assignment assignment = assignmentService.returnDevice(id);
            redirectAttributes.addFlashAttribute("success", "Device returned successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error returning device: " + e.getMessage());
        }
        
        return "redirect:/assignments";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteAssignment(@PathVariable Long id,
                                  RedirectAttributes redirectAttributes,
                                  HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        try {
            assignmentService.deleteAssignment(id);
            redirectAttributes.addFlashAttribute("success", "Assignment deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting assignment: " + e.getMessage());
        }
        
        return "redirect:/assignments";
    }
    
    @GetMapping("/device/{deviceId}")
    public String viewDeviceAssignments(@PathVariable Long deviceId,
                                       Model model,
                                       HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        Optional<Device> deviceOpt = deviceService.findById(deviceId);
        if (deviceOpt.isEmpty()) {
            return "redirect:/devices";
        }
        
        List<Assignment> assignments = assignmentService.findAssignmentsByDevice(deviceId);
        model.addAttribute("device", deviceOpt.get());
        model.addAttribute("assignments", assignments);
        
        return "assignments/device-assignments";
    }
    
    @GetMapping("/employee/{employeeId}")
    public String viewEmployeeAssignments(@PathVariable Long employeeId,
                                         Model model,
                                         HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            return "redirect:/employees";
        }
        
        List<Assignment> assignments = assignmentService.findAssignmentsByEmployee(employeeId);
        model.addAttribute("employee", employeeOpt.get());
        model.addAttribute("assignments", assignments);
        
        return "assignments/employee-assignments";
    }
}
