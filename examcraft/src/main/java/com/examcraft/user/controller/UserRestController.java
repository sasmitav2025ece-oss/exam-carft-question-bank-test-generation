package com.examcraft.user.controller;

import com.examcraft.user.entity.User;
import com.examcraft.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService service;

    public UserRestController(UserService service) { this.service = service; }

    @GetMapping
    public List<User> all() { return service.findAll(); }

    @PostMapping
    public User create(@Valid @RequestBody User user) { return service.save(user); }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) { return service.findById(id); }
}
