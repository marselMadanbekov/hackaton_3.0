package com.example.hackaton_back.repositories.petitions;

import com.example.hackaton_back.entities.petitions.Petition;
import com.example.hackaton_back.entities.petitions.PetitionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetitionCommentRepository extends JpaRepository<PetitionComment, Long> {
    List<PetitionComment> findByPetition(Petition petition);
}
