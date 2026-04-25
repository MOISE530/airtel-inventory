package com.airtel.inventory.controller;

import com.airtel.inventory.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
    
    @Autowired
    private DeviceService deviceService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("fullName", session.getAttribute("fullName"));
        model.addAttribute("totalDevices", deviceService.count());
        model.addAttribute("availableDevices", deviceService.countAvailable());
        
        return "dashboard";
    }
    
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
}
