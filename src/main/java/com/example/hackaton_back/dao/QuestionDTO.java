package com.example.hackaton_back.dao;

import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String question;
    private String a;
    private String b;
    private String c;
    private String d;
}
