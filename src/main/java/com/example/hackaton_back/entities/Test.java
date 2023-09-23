package com.example.hackaton_back.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @ElementCollection
    private Map<Question, Integer> answers = new HashMap<>();
    private Integer duration;
    private LocalDateTime startTime;

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
