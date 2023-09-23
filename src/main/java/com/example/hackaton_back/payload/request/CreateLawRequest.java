package com.example.hackaton_back.payload.request;

import lombok.Data;

@Data
public class CreateLawRequest {
    private String title;
    private String law;
    private Long chapterId;
}
