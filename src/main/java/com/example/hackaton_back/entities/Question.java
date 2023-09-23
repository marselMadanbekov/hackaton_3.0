package com.example.hackaton_back.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private Integer answer;
    private String a;
    private String b;
    private String c;
    private String d;

    private Date createDate;
    private Date lastUpdate;

    @PrePersist
    private void onCreate(){
        this.createDate = Date.valueOf(LocalDate.now());
        lastUpdate = Date.valueOf(LocalDate.now());
    }

    public void updated(){
        this.lastUpdate = Date.valueOf(LocalDate.now());
    }
}
