package com.example.hackaton_back.entities.laws;

import com.example.hackaton_back.entities.laws.LawCategory;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private LawCategory category;


    public Section(){}
    public Section(String name, LawCategory category) {
        this.name = name;
        this.category = category;
    }
}
