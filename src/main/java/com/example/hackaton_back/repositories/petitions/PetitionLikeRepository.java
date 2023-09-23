package com.example.hackaton_back.repositories.petitions;

import com.example.hackaton_back.entities.petitions.Petition;
import com.example.hackaton_back.entities.petitions.PetitionLike;
import com.example.hackaton_back.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetitionLikeRepository extends JpaRepository<PetitionLike, Long> {
    Optional<PetitionLike> getPetitionLikeByUserAndPetition(User user, Petition petition);

    @Query("SELECT COUNT(pl) FROM PetitionLike pl WHERE pl.petition = :petition AND pl.isLike = :isLike")
    Optional<Integer> countByPetitionAndIsLike(@Param("petition") Petition petition, @Param("isLike") Boolean isLike);

}
