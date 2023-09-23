package com.example.hackaton_back.entities.laws;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Section section;

    public Chapter(){}
    public Chapter(String name, Section section){
        this.name = name;
        this.section = section;
    }
}
