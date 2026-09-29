package com.examcraft.question.repository;

import com.examcraft.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByDifficultyIgnoreCase(String difficulty);
    List<Question> findByTopicId(Long topicId);
    List<Question> findByTopicIdAndDifficultyIgnoreCase(Long topicId, String difficulty);
}
