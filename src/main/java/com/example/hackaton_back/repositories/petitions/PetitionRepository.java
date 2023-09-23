package com.example.hackaton_back.repositories.petitions;

import com.example.hackaton_back.entities.petitions.Petition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionRepository extends JpaRepository<Petition,Long> {
}
