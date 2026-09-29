package com.examcraft.topic.controller;

import com.examcraft.topic.entity.Topic;
import com.examcraft.topic.service.TopicService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicRestController {
    private final TopicService service;
    public TopicRestController(TopicService service) { this.service = service; }

    @GetMapping public List<Topic> all() { return service.all(); }
    @GetMapping("/{id}") public Topic get(@PathVariable Long id) { return service.get(id); }
    @PostMapping public Topic create(@RequestBody Topic topic) { return service.save(topic); }
    @PutMapping("/{id}")
    public Topic update(@PathVariable Long id, @RequestBody Topic topic) {
        Topic old = service.get(id);
        old.setName(topic.getName());
        old.setDescription(topic.getDescription());
        return service.save(old);
    }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}
