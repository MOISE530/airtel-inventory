package com.airtel.inventory.controller;

import com.airtel.inventory.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {
    
    @Autowired
    private AnalyticsService analyticsService;
    
    @GetMapping("")
    public String analyticsDashboard(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        Map<String, Object> analytics = analyticsService.getDashboardAnalytics();
        model.addAttribute("analytics", analytics);
        
        return "analytics/dashboard";
    }
    
    @GetMapping("/device/{id}")
    public String deviceAnalytics(@PathVariable Long id, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        Map<String, Object> analytics = analyticsService.getDeviceAnalytics(id);
        if (analytics.isEmpty()) {
            return "redirect:/devices";
        }
        
        model.addAttribute("analytics", analytics);
        return "analytics/device";
    }
    
    @GetMapping("/employee/{id}")
    public String employeeAnalytics(@PathVariable Long id, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        Map<String, Object> analytics = analyticsService.getEmployeeAnalytics(id);
        if (analytics.isEmpty()) {
            return "redirect:/employees";
        }
        
        model.addAttribute("analytics", analytics);
        return "analytics/employee";
    }
    
    @GetMapping("/api/dashboard")
    @ResponseBody
    public Map<String, Object> getDashboardAnalyticsApi(HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return Map.of("error", "Unauthorized");
        }
        
        return analyticsService.getDashboardAnalytics();
    }
    
    @GetMapping("/api/device/{id}")
    @ResponseBody
    public Map<String, Object> getDeviceAnalyticsApi(@PathVariable Long id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return Map.of("error", "Unauthorized");
        }
        
        return analyticsService.getDeviceAnalytics(id);
    }
    
    @GetMapping("/api/employee/{id}")
    @ResponseBody
    public Map<String, Object> getEmployeeAnalyticsApi(@PathVariable Long id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return Map.of("error", "Unauthorized");
        }
        
        return analyticsService.getEmployeeAnalytics(id);
    }
}
