package com.example.hackaton_back.entities.petitions;

import com.example.hackaton_back.entities.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PetitionCommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PetitionComment comment;
    @ManyToOne
    private User user;
    private Boolean isLike;
}
