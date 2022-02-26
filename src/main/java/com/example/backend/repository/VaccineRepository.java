package com.example.backend.repository;

import com.example.backend.models.medical_information.EVaccineType;
import com.example.backend.models.medical_information.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VaccineRepository extends JpaRepository<VaccineType, Long> {
    Optional<VaccineType> findByName(EVaccineType name);
}
