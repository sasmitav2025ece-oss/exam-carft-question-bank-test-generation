package com.examcraft.testpaper.controller;

import com.examcraft.testpaper.entity.TestPaper;
import com.examcraft.testpaper.entity.TestPaperQuestion;
import com.examcraft.testpaper.service.TestPaperService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/testpapers")
public class TestPaperRestController {
    private final TestPaperService service;
    public TestPaperRestController(TestPaperService service) { this.service = service; }

    @GetMapping public List<TestPaper> all() { return service.all(); }
    @GetMapping("/{id}") public TestPaper get(@PathVariable Long id) { return service.get(id); }

    @PostMapping("/generate")
    public TestPaper generate(@RequestParam String title,
                              @RequestParam int easy,
                              @RequestParam int medium,
                              @RequestParam int hard) {
        return service.generate(title, easy, medium, hard);
    }

    @GetMapping("/{id}/questions")
    public List<TestPaperQuestion> questions(@PathVariable Long id) {
        return service.questions(id);
    }
}
