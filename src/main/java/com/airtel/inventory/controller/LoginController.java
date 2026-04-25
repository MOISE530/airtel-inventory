package com.airtel.inventory.controller;

import com.airtel.inventory.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error) {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password, 
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        
        var user = userRepository.findByUsernameAndPassword(username, password);
        
        if (user.isPresent()) {
            session.setAttribute("username", user.get().getUsername());
            session.setAttribute("fullName", user.get().getFullName());
            session.setAttribute("role", user.get().getRole());
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
