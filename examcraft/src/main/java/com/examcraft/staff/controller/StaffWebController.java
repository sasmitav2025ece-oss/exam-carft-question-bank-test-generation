package com.examcraft.staff.controller;

import com.examcraft.question.entity.Question;
import com.examcraft.question.service.QuestionService;
import com.examcraft.testpaper.service.TestPaperService;
import com.examcraft.topic.entity.Topic;
import com.examcraft.topic.service.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/staff")
public class StaffWebController {
    private final QuestionService questionService;
    private final TopicService topicService;
    private final TestPaperService testPaperService;

    public StaffWebController(QuestionService questionService, TopicService topicService, TestPaperService testPaperService) {
        this.questionService = questionService;
        this.topicService = topicService;
        this.testPaperService = testPaperService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("questionCount", questionService.all().size());
        model.addAttribute("topicCount", topicService.all().size());
        model.addAttribute("paperCount", testPaperService.all().size());
        return "staff-dashboard";
    }

    @GetMapping("/questions")
    public String questions(Model model) {
        model.addAttribute("questions", questionService.all());
        model.addAttribute("topics", topicService.all());
        model.addAttribute("question", new Question());
        return "questions";
    }

    @PostMapping("/questions")
    public String addQuestion(@RequestParam Long topicId,
                              @RequestParam String questionText,
                              @RequestParam String difficulty,
                              @RequestParam String optionA,
                              @RequestParam String optionB,
                              @RequestParam String optionC,
                              @RequestParam String optionD,
                              @RequestParam String correctOption) {
        Question q = new Question();
        q.setQuestionText(questionText);
        q.setDifficulty(difficulty);
        q.setOptions(Arrays.asList(optionA, optionB, optionC, optionD));
        q.setCorrectOption(correctOption);
        questionService.save(q, topicId);
        return "redirect:/staff/questions";
    }

    @PostMapping("/questions/{id}/delete")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.delete(id);
        return "redirect:/staff/questions";
    }

    @GetMapping("/generate")
    public String generatePage(Model model) {
        model.addAttribute("papers", testPaperService.all());
        return "generate-test";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam String title,
                           @RequestParam int easy,
                           @RequestParam int medium,
                           @RequestParam int hard,
                           Model model) {
        try {
            testPaperService.generate(title, easy, medium, hard);
            return "redirect:/staff/generate";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("papers", testPaperService.all());
            return "generate-test";
        }
    }

    @GetMapping("/topics")
    public String topics(Model model) {
        model.addAttribute("topics", topicService.all());
        return "topics";
    }

    @PostMapping("/topics")
    public String addTopic(@RequestParam String name, @RequestParam String description) {
        topicService.save(new Topic(name, description));
        return "redirect:/staff/topics";
    }
}
