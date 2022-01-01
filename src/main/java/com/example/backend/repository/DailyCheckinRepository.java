package com.example.backend.repository;

import com.example.backend.models.medical_information.DailyCheckin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyCheckinRepository extends JpaRepository<DailyCheckin, Long> {
    @Query(value = "SELECT j FROM DailyCheckin j  WHERE date_record between CONCAT(?1,' ','00:00:00') and CONCAT(?1,' ','23:59:59') ")
    List<DailyCheckin> search(LocalDate date);

    @Query(value = "SELECT j FROM DailyCheckin j")
    List<DailyCheckin> findAll();

}
