package com.examcraft.question.controller;

import com.examcraft.question.entity.Question;
import com.examcraft.question.service.QuestionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionRestController {
    private final QuestionService service;
    public QuestionRestController(QuestionService service) { this.service = service; }

    @GetMapping public List<Question> all() { return service.all(); }
    @GetMapping("/{id}") public Question get(@PathVariable Long id) { return service.get(id); }

    @PostMapping
    public Question create(@RequestParam Long topicId, @RequestBody Question question) {
        return service.save(question, topicId);
    }

    @PutMapping("/{id}")
    public Question update(@PathVariable Long id, @RequestParam Long topicId, @RequestBody Question question) {
        Question old = service.get(id);
        old.setQuestionText(question.getQuestionText());
        old.setDifficulty(question.getDifficulty());
        old.setOptions(question.getOptions());
        old.setCorrectOption(question.getCorrectOption());
        return service.save(old, topicId);
    }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}
