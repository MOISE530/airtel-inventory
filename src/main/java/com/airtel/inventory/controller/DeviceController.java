package com.airtel.inventory.controller;

import com.airtel.inventory.model.Device;
import com.airtel.inventory.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/devices")
public class DeviceController {
    
    @Autowired
    private DeviceService deviceService;
    
    @GetMapping("/")
    public String listDevices(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("devices", deviceService.findAll());
        return "devices/list";
    }
    
    @GetMapping("")
    public String listDevicesNoSlash(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("devices", deviceService.findAll());
        return "devices/list";
    }
    
    @GetMapping("/search")
    public String searchDevices(@RequestParam(required = false) String keyword,
                                @RequestParam(required = false) String deviceType,
                                @RequestParam(required = false) String brand,
                                @RequestParam(required = false) String status,
                                @RequestParam(required = false) String condition,
                                Model model, HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        List<Device> devices;
        if (keyword != null && !keyword.trim().isEmpty()) {
            devices = deviceService.searchDevices(keyword.trim());
        } else if (deviceType != null || brand != null || status != null || condition != null) {
            devices = deviceService.filterDevices(deviceType, brand, status, condition);
        } else {
            devices = deviceService.findAll();
        }
        
        model.addAttribute("devices", devices);
        model.addAttribute("searchKeyword", keyword);
        model.addAttribute("filterDeviceType", deviceType);
        model.addAttribute("filterBrand", brand);
        model.addAttribute("filterStatus", status);
        model.addAttribute("filterCondition", condition);
        model.addAttribute("searchPerformed", true);
        
        return "devices/list";
    }
    
    @GetMapping("/add")
    public String showAddDeviceForm(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("device", new Device());
        model.addAttribute("isEdit", false);
        return "devices/form";
    }
    
    @PostMapping("/add")
    public String addDevice(@ModelAttribute Device device, 
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        try {
            deviceService.save(device);
            redirectAttributes.addFlashAttribute("success", "Device added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding device: " + e.getMessage());
        }
        
        return "redirect:/devices";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteDevice(@PathVariable Long id, 
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        try {
            deviceService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Device deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting device: " + e.getMessage());
        }
        
        return "redirect:/devices";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditDeviceForm(@PathVariable Long id, 
                                     Model model,
                                     HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        var deviceOpt = deviceService.findById(id);
        if (deviceOpt.isEmpty()) {
            return "redirect:/devices";
        }
        
        model.addAttribute("device", deviceOpt.get());
        model.addAttribute("isEdit", true);
        
        return "devices/form";
    }
    
    @PostMapping("/edit/{id}")
    public String updateDevice(@PathVariable Long id,
                              @ModelAttribute Device device,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        try {
            device.setId(id);
            deviceService.save(device);
            redirectAttributes.addFlashAttribute("success", "Device updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating device: " + e.getMessage());
        }
        
        return "redirect:/devices";
    }
    
    @GetMapping("/view/{id}")
    public String viewDevice(@PathVariable Long id,
                            Model model,
                            HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        var deviceOpt = deviceService.findById(id);
        if (deviceOpt.isEmpty()) {
            return "redirect:/devices";
        }
        
        model.addAttribute("device", deviceOpt.get());
        
        return "devices/view";
    }
}
