package com.example.hackaton_back.dao;

import com.example.hackaton_back.entities.User;
import lombok.Data;

@Data
public class PetitionDTO {
    private Long id;
    private User creator;
    private String ruTitle;
    private String kgTitle;
    private String ruDescription;
    private String kgDescription;
    private String photo;
    private Boolean isFollow;
}
