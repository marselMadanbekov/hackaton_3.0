package com.example.hackaton_back.entities.petitions;

import com.example.hackaton_back.entities.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PetitionLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Petition petition;

    private Boolean isLike;
}
