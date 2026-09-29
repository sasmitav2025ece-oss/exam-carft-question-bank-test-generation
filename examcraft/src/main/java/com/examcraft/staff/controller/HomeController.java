package com.examcraft.staff.controller;

import com.examcraft.user.entity.User;
import com.examcraft.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) { this.userService = userService; }

    @GetMapping("/")
    public String home() { return "index"; }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam String role) {
        try {
            User user = userService.findByEmail(email);
            if (user.getPassword().equals(password) && user.getRole().equalsIgnoreCase(role)) {
                if ("ADMIN".equalsIgnoreCase(role)) return "redirect:/staff/dashboard";
                return "redirect:/student/dashboard";
            }
        } catch (Exception ignored) {}
        return "redirect:/?error=1";
    }
}
