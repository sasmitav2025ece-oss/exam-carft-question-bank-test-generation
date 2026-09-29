package com.examcraft.testpaper.entity;

import com.examcraft.attempt.entity.TestAttempt;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_papers")
public class TestPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "total_questions", nullable = false)
    private Integer totalQuestions;

    @Column(name = "difficulty_distribution")
    private String difficultyDistribution;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "testPaper", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestPaperQuestion> questions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "testPaper")
    private List<TestAttempt> attempts = new ArrayList<>();

    @PrePersist
    public void prePersist() { if (createdAt == null) createdAt = LocalDateTime.now(); }

    public TestPaper() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(Integer totalQuestions) { this.totalQuestions = totalQuestions; }
    public String getDifficultyDistribution() { return difficultyDistribution; }
    public void setDifficultyDistribution(String difficultyDistribution) { this.difficultyDistribution = difficultyDistribution; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<TestPaperQuestion> getQuestions() { return questions; }
    public void setQuestions(List<TestPaperQuestion> questions) { this.questions = questions; }
    public List<TestAttempt> getAttempts() { return attempts; }
    public void setAttempts(List<TestAttempt> attempts) { this.attempts = attempts; }
}
