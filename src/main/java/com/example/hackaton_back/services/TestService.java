package com.example.hackaton_back.services;

import com.example.hackaton_back.dao.TestDTO;
import com.example.hackaton_back.entities.Question;
import com.example.hackaton_back.entities.Test;
import com.example.hackaton_back.entities.User;
import com.example.hackaton_back.repositories.QuestionRepository;
import com.example.hackaton_back.repositories.TestRepository;
import com.example.hackaton_back.repositories.UserRepository;
import com.example.hackaton_back.services.facades.TestFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TestService {
    private final TestRepository testRepository;
    private final TestFacade testFacade;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Autowired
    public TestService(TestRepository testRepository, TestFacade testFacade, QuestionRepository questionRepository, UserRepository userRepository) {
        this.testRepository = testRepository;
        this.testFacade = testFacade;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public TestDTO createTest(String email){
        Test test = new Test();
        User user = getUserByEmail(email);

        test.setUser(user);
        test.setDuration(3);
        test.setStartTime(LocalDateTime.now());
        List<Question> questions = questionRepository.findRandomQuestions();

        for(Question question : questions){
            test.getAnswers().put(question, 0);
        }
        testRepository.save(test);
        return testFacade.testToTestDTO(test);
    }

    private User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }
}
