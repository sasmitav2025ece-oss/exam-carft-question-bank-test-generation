package com.examcraft.attempt.controller;

import com.examcraft.attempt.entity.TestAttempt;
import com.examcraft.attempt.service.TestAttemptService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attempts")
public class TestAttemptRestController {
    private final TestAttemptService service;
    public TestAttemptRestController(TestAttemptService service) { this.service = service; }

    @GetMapping public List<TestAttempt> all() { return service.all(); }
    @GetMapping("/{id}") public TestAttempt get(@PathVariable Long id) { return service.get(id); }

    @PostMapping
    public TestAttempt submit(@RequestParam Long userId,
                              @RequestParam Long testPaperId,
                              @RequestBody Map<Long, String> answers) {
        return service.submit(userId, testPaperId, answers);
    }

    @GetMapping("/user/{userId}")
    public List<TestAttempt> byUser(@PathVariable Long userId) {
        return service.byUser(userId);
    }
}
