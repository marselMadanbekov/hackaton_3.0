package com.example.hackaton_back.services.facades;

import com.example.hackaton_back.dao.TestDTO;
import com.example.hackaton_back.entities.Question;
import com.example.hackaton_back.entities.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestFacade {
    private final QuestionFacade questionFacade;

    @Autowired
    public TestFacade(QuestionFacade questionFacade) {
        this.questionFacade = questionFacade;
    }

    public TestDTO testToTestDTO(Test test){
        TestDTO testDTO = new TestDTO();
        testDTO.setId(test.getId());
        testDTO.setDuration(test.getDuration());
        testDTO.setStartTime(test.getStartTime());
        for(Question question : test.getAnswers().keySet()){
            testDTO.getQuestions().add(questionFacade.questionToQuestionDTO(question));
        }
        return testDTO;
    }
}
