package com.examcraft.staff.service;

import com.examcraft.question.entity.Question;
import com.examcraft.question.repository.QuestionRepository;
import com.examcraft.topic.entity.Topic;
import com.examcraft.topic.repository.TopicRepository;
import com.examcraft.user.entity.User;
import com.examcraft.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DemoDataService implements CommandLineRunner {
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;

    public DemoDataService(UserRepository userRepository, TopicRepository topicRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(new User("Admin Staff", "admin@examcraft.com", "admin123", "ADMIN"));
            userRepository.save(new User("Demo Student", "student@examcraft.com", "student123", "STUDENT"));
        }

        if (topicRepository.count() == 0) {
            Topic java = topicRepository.save(new Topic("Java", "Java programming and OOP"));
            Topic dbms = topicRepository.save(new Topic("DBMS", "Database management systems"));
            addQuestion(java, "Which keyword is used to inherit a class in Java?", "Easy", "extends");
            addQuestion(java, "Which concept allows one interface with many implementations?", "Medium", "Polymorphism");
            addQuestion(java, "Which collection does not allow duplicate elements?", "Hard", "Set");
            addQuestion(dbms, "What does SQL stand for?", "Easy", "Structured Query Language");
            addQuestion(dbms, "Which normal form removes partial dependency?", "Medium", "2NF");
            addQuestion(dbms, "Which property guarantees all-or-nothing transaction execution?", "Hard", "Atomicity");
        }
    }

    private void addQuestion(Topic topic, String text, String difficulty, String correct) {
        Question q = new Question();
        q.setTopic(topic);
        q.setQuestionText(text);
        q.setDifficulty(difficulty);
        if (correct.equals("extends")) {
            q.setOptions(Arrays.asList("implements", "extends", "inherit", "super"));
            q.setCorrectOption("B");
        } else if (correct.equals("Polymorphism")) {
            q.setOptions(Arrays.asList("Encapsulation", "Inheritance", "Polymorphism", "Abstraction"));
            q.setCorrectOption("C");
        } else if (correct.equals("Set")) {
            q.setOptions(Arrays.asList("List", "Map", "Set", "Queue"));
            q.setCorrectOption("C");
        } else if (correct.equals("Structured Query Language")) {
            q.setOptions(Arrays.asList("Structured Query Language", "Simple Query Language", "System Query Logic", "Sequential Query Language"));
            q.setCorrectOption("A");
        } else if (correct.equals("2NF")) {
            q.setOptions(Arrays.asList("1NF", "2NF", "3NF", "BCNF"));
            q.setCorrectOption("B");
        } else {
            q.setOptions(Arrays.asList("Consistency", "Isolation", "Atomicity", "Durability"));
            q.setCorrectOption("C");
        }
        questionRepository.save(q);
    }
}
