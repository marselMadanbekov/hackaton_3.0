package com.example.hackaton_back.entities.petitions;

import com.example.hackaton_back.entities.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
public class PetitionComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Petition petition;
    private String comment;

    private Date createDate;
    private Date lastUpdate;

    @PrePersist
    private void onCreate(){
        this.createDate = Date.valueOf(LocalDate.now());
        this.lastUpdate = Date.valueOf(LocalDate.now());
    }
}
