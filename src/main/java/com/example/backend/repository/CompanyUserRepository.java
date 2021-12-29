package com.example.backend.repository;

import com.example.backend.models.Account;
import com.example.backend.models.user.CompanyUserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUserInformation,Long> {


}
