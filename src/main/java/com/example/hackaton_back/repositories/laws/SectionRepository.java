package com.example.hackaton_back.repositories.laws;

import com.example.hackaton_back.entities.laws.LawCategory;
import com.example.hackaton_back.entities.laws.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByCategory(LawCategory category);
}
