package com.examcraft.testpaper.service;

import com.examcraft.question.entity.Question;
import com.examcraft.question.service.QuestionService;
import com.examcraft.testpaper.entity.TestPaper;
import com.examcraft.testpaper.entity.TestPaperQuestion;
import com.examcraft.testpaper.repository.TestPaperQuestionRepository;
import com.examcraft.testpaper.repository.TestPaperRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TestPaperService {
    private final TestPaperRepository paperRepository;
    private final TestPaperQuestionRepository paperQuestionRepository;
    private final QuestionService questionService;

    public TestPaperService(TestPaperRepository paperRepository,
                            TestPaperQuestionRepository paperQuestionRepository,
                            QuestionService questionService) {
        this.paperRepository = paperRepository;
        this.paperQuestionRepository = paperQuestionRepository;
        this.questionService = questionService;
    }

    public List<TestPaper> all() { return paperRepository.findAll(); }

    @Transactional
    public TestPaper generate(String title, int easy, int medium, int hard) {
        if (easy < 0 || medium < 0 || hard < 0) {
            throw new IllegalArgumentException("Question counts cannot be negative");
        }
        int total = easy + medium + hard;
        if (total == 0) throw new IllegalArgumentException("At least one question is required");

        List<Question> easyQuestions = new ArrayList<>(questionService.byDifficulty("Easy"));
        List<Question> mediumQuestions = new ArrayList<>(questionService.byDifficulty("Medium"));
        List<Question> hardQuestions = new ArrayList<>(questionService.byDifficulty("Hard"));

        Collections.shuffle(easyQuestions);
        Collections.shuffle(mediumQuestions);
        Collections.shuffle(hardQuestions);

        if (easyQuestions.size() < easy)
            throw new IllegalArgumentException("Not enough Easy questions. Available: " + easyQuestions.size());
        if (mediumQuestions.size() < medium)
            throw new IllegalArgumentException("Not enough Medium questions. Available: " + mediumQuestions.size());
        if (hardQuestions.size() < hard)
            throw new IllegalArgumentException("Not enough Hard questions. Available: " + hardQuestions.size());

        List<Question> selected = new ArrayList<>();
        selected.addAll(easyQuestions.subList(0, easy));
        selected.addAll(mediumQuestions.subList(0, medium));
        selected.addAll(hardQuestions.subList(0, hard));
        Collections.shuffle(selected);

        TestPaper paper = new TestPaper();
        paper.setTitle(title);
        paper.setTotalQuestions(total);
        paper.setDifficultyDistribution("Easy=" + easy + ", Medium=" + medium + ", Hard=" + hard);
        paper = paperRepository.save(paper);

        int sequence = 1;
        for (Question question : selected) {
            TestPaperQuestion tpq = new TestPaperQuestion(paper, question, sequence++);
            paper.getQuestions().add(tpq);
            paperQuestionRepository.save(tpq);
        }
        return paper;
    }

    @Transactional(readOnly = true)
    public TestPaper get(Long id) {
        TestPaper paper = paperRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Test paper not found"));
        paper.getQuestions().sort(Comparator.comparing(TestPaperQuestion::getSequenceNo));
        return paper;
    }

    public List<TestPaperQuestion> questions(Long paperId) {
        return paperQuestionRepository.findByTestPaperIdOrderBySequenceNo(paperId);
    }
}
