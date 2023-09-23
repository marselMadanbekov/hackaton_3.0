package com.example.hackaton_back.services;

import com.example.hackaton_back.entities.Question;
import com.example.hackaton_back.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(String question, String a, String b, String c, String d, Integer answer){
        Question newQuestion = new Question();
        newQuestion.setQuestion(question);
        newQuestion.setA(a);
        newQuestion.setB(b);
        newQuestion.setC(c);
        newQuestion.setD(d);
        newQuestion.setAnswer(answer);
        return questionRepository.save(newQuestion);
    }
}
