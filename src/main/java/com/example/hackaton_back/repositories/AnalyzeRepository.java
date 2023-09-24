package com.example.hackaton_back.repositories;

import com.example.hackaton_back.entities.Analyze;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyzeRepository extends JpaRepository<Analyze, Long> {
}
