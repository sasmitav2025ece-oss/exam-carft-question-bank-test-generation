package com.examcraft.student.controller;

import com.examcraft.attempt.service.TestAttemptService;
import com.examcraft.testpaper.entity.TestPaperQuestion;
import com.examcraft.testpaper.service.TestPaperService;
import com.examcraft.user.entity.User;
import com.examcraft.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentWebController {
    private final TestPaperService paperService;
    private final TestAttemptService attemptService;
    private final UserService userService;

    public StudentWebController(TestPaperService paperService, TestAttemptService attemptService, UserService userService) {
        this.paperService = paperService;
        this.attemptService = attemptService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("papers", paperService.all());
        return "student-dashboard";
    }

    @GetMapping("/test/{id}")
    public String test(@PathVariable Long id, Model model) {
        model.addAttribute("paper", paperService.get(id));
        model.addAttribute("items", paperService.questions(id));
        return "attempt-test";
    }

    @PostMapping("/test/{id}/submit")
    public String submit(@PathVariable Long id,
                         @RequestParam Long userId,
                         @RequestParam Map<String, String> form,
                         Model model) {
        Map<Long, String> answers = new HashMap<>();
        for (Map.Entry<String, String> entry : form.entrySet()) {
            if (entry.getKey().startsWith("q_")) {
                answers.put(Long.valueOf(entry.getKey().substring(2)), entry.getValue());
            }
        }
        var attempt = attemptService.submit(userId, id, answers);
        model.addAttribute("attempt", attempt);
        return "result";
    }
}
