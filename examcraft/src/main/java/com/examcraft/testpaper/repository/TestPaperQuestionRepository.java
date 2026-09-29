package com.examcraft.testpaper.repository;

import com.examcraft.testpaper.entity.TestPaperQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TestPaperQuestionRepository extends JpaRepository<TestPaperQuestion, Long> {
    List<TestPaperQuestion> findByTestPaperIdOrderBySequenceNo(Long testPaperId);
}
