package com.example.hackaton_back.repositories;

import com.example.hackaton_back.entities.petitions.Petition;
import com.example.hackaton_back.entities.petitions.PetitionLike;
import com.example.hackaton_back.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetitionLikeRepository extends JpaRepository<PetitionLike, Long> {
    Optional<PetitionLike> getPetitionLikeByUserAndPetition(User user, Petition petition);
}
