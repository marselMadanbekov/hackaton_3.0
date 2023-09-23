package com.example.hackaton_back.dao;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TestDTO {
    private Long id;
    private Integer duration;
    private LocalDateTime startTime;
    private List<QuestionDTO> questions = new ArrayList<>();
}
