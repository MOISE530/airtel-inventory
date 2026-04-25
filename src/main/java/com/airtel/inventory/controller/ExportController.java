package com.airtel.inventory.controller;

import com.airtel.inventory.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@Controller
@RequestMapping("/export")
public class ExportController {
    
    @Autowired
    private ExportService exportService;
    
    @GetMapping("/devices/csv")
    public ResponseEntity<byte[]> exportDevicesToCSV(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(302).build();
        }
        
        try {
            var outputStream = exportService.exportDevicesToCSV();
            byte[] data = outputStream.toByteArray();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/csv"));
            headers.setContentDispositionFormData("attachment", 
                exportService.generateFilename("devices", "csv"));
            headers.setContentLength(data.length);
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(data);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/employees/csv")
    public ResponseEntity<byte[]> exportEmployeesToCSV(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(302).build();
        }
        
        try {
            var outputStream = exportService.exportEmployeesToCSV();
            byte[] data = outputStream.toByteArray();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/csv"));
            headers.setContentDispositionFormData("attachment", 
                exportService.generateFilename("employees", "csv"));
            headers.setContentLength(data.length);
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(data);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/assignments/csv")
    public ResponseEntity<byte[]> exportAssignmentsToCSV(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(302).build();
        }
        
        try {
            var outputStream = exportService.exportAssignmentsToCSV();
            byte[] data = outputStream.toByteArray();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/csv"));
            headers.setContentDispositionFormData("attachment", 
                exportService.generateFilename("assignments", "csv"));
            headers.setContentLength(data.length);
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(data);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/summary/txt")
    public ResponseEntity<byte[]> exportInventorySummary(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(302).build();
        }
        
        try {
            var outputStream = exportService.generateInventorySummary();
            byte[] data = outputStream.toByteArray();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/plain"));
            headers.setContentDispositionFormData("attachment", 
                exportService.generateFilename("summary", "txt"));
            headers.setContentLength(data.length);
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(data);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
