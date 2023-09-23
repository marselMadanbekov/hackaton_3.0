package com.example.hackaton_back.repositories.laws;

import com.example.hackaton_back.entities.laws.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}
