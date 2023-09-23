package com.example.hackaton_back.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class CreatePetitionRequest {
    private String email;
    private String ruTitle;
    private String kgTitle;
    private String ruDescription;
    private String kgDescription;
    private MultipartFile photo;
}
