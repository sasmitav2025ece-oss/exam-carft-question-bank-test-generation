package com.examcraft.question.service;

import com.examcraft.question.entity.Question;
import com.examcraft.question.repository.QuestionRepository;
import com.examcraft.topic.entity.Topic;
import com.examcraft.topic.service.TopicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository repository;
    private final TopicService topicService;

    public QuestionService(QuestionRepository repository, TopicService topicService) {
        this.repository = repository;
        this.topicService = topicService;
    }

    public List<Question> all() { return repository.findAll(); }
    public Question get(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Question not found"));
    }
    public Question save(Question q, Long topicId) {
        Topic topic = topicService.get(topicId);
        q.setTopic(topic);
        return repository.save(q);
    }
    public Question save(Question q) { return repository.save(q); }
    public void delete(Long id) { repository.deleteById(id); }

    public List<Question> byDifficulty(String difficulty) {
        return repository.findByDifficultyIgnoreCase(difficulty);
    }

    public List<Question> byTopicAndDifficulty(Long topicId, String difficulty) {
        return repository.findByTopicIdAndDifficultyIgnoreCase(topicId, difficulty);
    }
}
