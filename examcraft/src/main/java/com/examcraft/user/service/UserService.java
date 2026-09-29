package com.examcraft.user.service;

import com.examcraft.user.entity.User;
import com.examcraft.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() { return repository.findAll(); }
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
    public User save(User user) { return repository.save(user); }
}
