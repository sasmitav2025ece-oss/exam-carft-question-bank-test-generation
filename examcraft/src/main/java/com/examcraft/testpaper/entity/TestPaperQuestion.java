package com.examcraft.testpaper.entity;

import com.examcraft.question.entity.Question;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "test_paper_questions",
       uniqueConstraints = @UniqueConstraint(columnNames = {"paper_id", "question_id"}))
public class TestPaperQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "paper_id")
    private TestPaper testPaper;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "sequence_no", nullable = false)
    private Integer sequenceNo;

    public TestPaperQuestion() {}
    public TestPaperQuestion(TestPaper testPaper, Question question, Integer sequenceNo) {
        this.testPaper = testPaper; this.question = question; this.sequenceNo = sequenceNo;
    }

    public Long getId() { return id; }
    public TestPaper getTestPaper() { return testPaper; }
    public void setTestPaper(TestPaper testPaper) { this.testPaper = testPaper; }
    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }
    public Integer getSequenceNo() { return sequenceNo; }
    public void setSequenceNo(Integer sequenceNo) { this.sequenceNo = sequenceNo; }
}
