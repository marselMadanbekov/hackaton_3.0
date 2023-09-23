package com.example.hackaton_back.controllers;

import com.example.hackaton_back.entities.Question;
import com.example.hackaton_back.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createComment(@RequestParam String question,
                                                @RequestParam String a,
                                                @RequestParam String b,
                                                @RequestParam String c,
                                                @RequestParam String d,
                                                @RequestParam Integer answer) {
        Map<String, String> response = new HashMap<>();
        try {
            Question question1 = questionService.createQuestion(question, a, b, c, d, answer);
            return ResponseEntity.ok(question1);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
