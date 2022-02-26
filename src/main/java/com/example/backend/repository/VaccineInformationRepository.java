package com.example.backend.repository;

import com.example.backend.models.medical_information.EVaccineType;
import com.example.backend.models.medical_information.VaccineInformation;
import com.example.backend.models.medical_information.VaccineType;
import com.example.backend.models.role.ERole;
import com.example.backend.models.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaccineInformationRepository extends JpaRepository<VaccineInformation, Long> {

}
