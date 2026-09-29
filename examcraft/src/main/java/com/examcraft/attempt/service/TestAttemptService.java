package com.examcraft.attempt.service;

import com.examcraft.attempt.entity.TestAttempt;
import com.examcraft.attempt.repository.TestAttemptRepository;
import com.examcraft.testpaper.entity.TestPaper;
import com.examcraft.testpaper.entity.TestPaperQuestion;
import com.examcraft.testpaper.service.TestPaperService;
import com.examcraft.user.entity.User;
import com.examcraft.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TestAttemptService {
    private final TestAttemptRepository repository;
    private final UserService userService;
    private final TestPaperService paperService;

    public TestAttemptService(TestAttemptRepository repository, UserService userService, TestPaperService paperService) {
        this.repository = repository;
        this.userService = userService;
        this.paperService = paperService;
    }

    public List<TestAttempt> all() { return repository.findAll(); }

    @Transactional
    public TestAttempt submit(Long userId, Long paperId, Map<Long, String> answers) {
        User user = userService.findById(userId);
        TestPaper paper = paperService.get(paperId);
        List<TestPaperQuestion> items = paperService.questions(paperId);

        int correct = 0;
        for (TestPaperQuestion item : items) {
            String selected = answers.get(item.getQuestion().getId());
            if (selected != null && selected.equals(item.getQuestion().getCorrectOption())) {
                correct++;
            }
        }

        double score = items.isEmpty() ? 0.0 : (correct * 100.0 / items.size());

        TestAttempt attempt = new TestAttempt();
        attempt.setUser(user);
        attempt.setTestPaper(paper);
        attempt.setStartTime(LocalDateTime.now());
        attempt.setEndTime(LocalDateTime.now());
        attempt.setScore(score);
        return repository.save(attempt);
    }

    public TestAttempt get(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Attempt not found"));
    }

    public List<TestAttempt> byUser(Long userId) { return repository.findByUserId(userId); }
}
