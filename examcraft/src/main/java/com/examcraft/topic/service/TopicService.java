package com.examcraft.topic.service;

import com.examcraft.topic.entity.Topic;
import com.examcraft.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TopicService {
    private final TopicRepository repository;
    public TopicService(TopicRepository repository) { this.repository = repository; }

    public List<Topic> all() { return repository.findAll(); }
    public Topic get(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Topic not found"));
    }
    public Topic save(Topic topic) { return repository.save(topic); }
    public void delete(Long id) { repository.deleteById(id); }
}
