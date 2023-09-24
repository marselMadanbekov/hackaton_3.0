package com.example.hackaton_back.repositories.laws;

import com.example.hackaton_back.entities.laws.Chapter;
import com.example.hackaton_back.entities.laws.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findBySection(Section section);
}
