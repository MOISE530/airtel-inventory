package com.airtel.inventory.controller;

import com.airtel.inventory.model.Employee;
import com.airtel.inventory.model.Assignment;
import com.airtel.inventory.service.EmployeeService;
import com.airtel.inventory.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private AssignmentService assignmentService;
    
    @GetMapping("/")
    public String listEmployees(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        model.addAttribute("totalEmployees", employeeService.count());
        
        return "employees/list";
    }
    
    @GetMapping("")
    public String listEmployeesNoSlash(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        model.addAttribute("totalEmployees", employeeService.count());
        
        return "employees/list";
    }
    
    @GetMapping("/add")
    public String showAddEmployeeForm(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("employee", new Employee());
        model.addAttribute("isEdit", false);
        
        return "employees/form";
    }
    
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee, 
                             RedirectAttributes redirectAttributes,
                             HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        try {
            // Check if employee ID already exists
            if (employeeService.existsByEmployeeId(employee.getEmployeeId())) {
                redirectAttributes.addFlashAttribute("error", "Employee ID already exists: " + employee.getEmployeeId());
                return "redirect:/employees/add";
            }
            
            employeeService.save(employee);
            redirectAttributes.addFlashAttribute("success", "Employee added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding employee: " + e.getMessage());
        }
        
        return "redirect:/employees";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, 
                                      Model model,
                                      HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        Optional<Employee> employeeOpt = employeeService.findById(id);
        if (employeeOpt.isEmpty()) {
            return "redirect:/employees";
        }
        
        model.addAttribute("employee", employeeOpt.get());
        model.addAttribute("isEdit", true);
        
        return "employees/form";
    }
    
    @PostMapping("/edit/{id}")
    public String updateEmployee(@PathVariable Long id,
                                @ModelAttribute Employee employee,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        try {
            employeeService.updateEmployee(id, employee);
            redirectAttributes.addFlashAttribute("success", "Employee updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating employee: " + e.getMessage());
        }
        
        return "redirect:/employees";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        try {
            // Check if employee has active assignments
            List<Assignment> activeAssignments = assignmentService.findAssignmentsByEmployee(id);
            boolean hasActiveAssignments = false;
            
            for (Assignment assignment : activeAssignments) {
                if (assignment.getReturnDate() == null) {
                    hasActiveAssignments = true;
                    break;
                }
            }
            
            if (hasActiveAssignments) {
                redirectAttributes.addFlashAttribute("error", "Cannot delete employee with active device assignments");
                return "redirect:/employees";
            }
            
            employeeService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Employee deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting employee: " + e.getMessage());
        }
        
        return "redirect:/employees";
    }
    
    @GetMapping("/view/{id}")
    public String viewEmployee(@PathVariable Long id,
                               Model model,
                               HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        Optional<Employee> employeeOpt = employeeService.findById(id);
        if (employeeOpt.isEmpty()) {
            return "redirect:/employees";
        }
        
        Employee employee = employeeOpt.get();
        List<?> assignments = assignmentService.findAssignmentsByEmployee(id);
        
        model.addAttribute("employee", employee);
        model.addAttribute("assignments", assignments);
        model.addAttribute("activeAssignmentsCount", assignments.stream()
                .mapToLong(a -> ((com.airtel.inventory.model.Assignment) a).getReturnDate() == null ? 1 : 0)
                .sum());
        
        return "employees/view";
    }
}
