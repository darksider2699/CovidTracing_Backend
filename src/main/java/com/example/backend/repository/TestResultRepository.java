package com.example.backend.repository;

import com.example.backend.models.medical_information.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult,Long> {
    @Query(value = "SELECT j FROM TestResult j  WHERE date_record between CONCAT(?1,' ','00:00:00') and CONCAT(?1,' ','23:59:59') ")
    List<TestResult> search(LocalDate date);

    @Query(value = "SELECT j FROM TestResult j")
    List<TestResult> findAll();

}
