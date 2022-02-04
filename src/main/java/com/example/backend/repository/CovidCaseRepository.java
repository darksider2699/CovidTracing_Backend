package com.example.backend.repository;

import com.example.backend.models.medical_information.CovidCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidCaseRepository extends JpaRepository<CovidCase, Long> {
}
