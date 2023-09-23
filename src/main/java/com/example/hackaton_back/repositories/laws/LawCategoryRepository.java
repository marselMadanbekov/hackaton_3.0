package com.example.hackaton_back.repositories.laws;

import com.example.hackaton_back.entities.laws.LawCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawCategoryRepository extends JpaRepository<LawCategory, Long> {
}
