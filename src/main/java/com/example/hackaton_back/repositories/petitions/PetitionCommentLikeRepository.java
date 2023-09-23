package com.example.hackaton_back.repositories.petitions;

import com.example.hackaton_back.entities.User;
import com.example.hackaton_back.entities.petitions.Petition;
import com.example.hackaton_back.entities.petitions.PetitionComment;
import com.example.hackaton_back.entities.petitions.PetitionCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetitionCommentLikeRepository extends JpaRepository<PetitionCommentLike, Long> {
    Optional<PetitionCommentLike> findByUserAndComment(User user, PetitionComment petitionComment);

    @Query("SELECT COUNT(pl) FROM PetitionCommentLike pl WHERE pl.comment = :comment AND pl.isLike = :isLike")
    Optional<Integer> countByCommentAndIsLike(@Param("comment") PetitionComment comment, @Param("isLike") Boolean isLike);
}
