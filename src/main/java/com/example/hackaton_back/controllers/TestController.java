package com.example.hackaton_back.controllers;

import com.example.hackaton_back.dao.TestDTO;
import com.example.hackaton_back.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> getTest(@PathVariable String email){
        Map<String, Object> response = new HashMap<>();
        try{
            TestDTO testDTO = testService.createTest(email);
            response.put("data", testDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (RuntimeException e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
