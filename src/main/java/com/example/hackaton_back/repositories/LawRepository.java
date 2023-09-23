package com.example.hackaton_back.repositories;

import com.example.hackaton_back.entities.laws.Law;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawRepository extends JpaRepository<Law,Long> {
}