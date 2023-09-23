package com.example.hackaton_back.services.facades;


import com.example.hackaton_back.dao.QuestionDTO;
import com.example.hackaton_back.entities.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionFacade {

    public QuestionDTO questionToQuestionDTO(Question question){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestion(question.getQuestion());
        questionDTO.setId(question.getId());
        questionDTO.setA(question.getA());
        questionDTO.setB(question.getB());
        questionDTO.setC(question.getC());
        questionDTO.setD(question.getD());
        return questionDTO;
    }
}
