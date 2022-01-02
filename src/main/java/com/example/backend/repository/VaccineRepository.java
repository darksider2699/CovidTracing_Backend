package com.example.backend.repository;

import com.example.backend.models.medical_information.VaccineInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<VaccineInformation, Long> {
}
