package com.example.hackaton_back.repositories;

import com.example.hackaton_back.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * FROM Question ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Question> findRandomQuestions();
}
