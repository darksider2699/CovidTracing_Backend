package com.example.backend.repository;

import com.example.backend.models.medical_information.DailyCheckin;
import com.example.backend.models.user.MedicalUserInformation;
import com.example.backend.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DailyCheckinRepository extends JpaRepository<DailyCheckin, Long> {
    @Query("SELECT j FROM DailyCheckin j  WHERE j.dateRecord = ?1 ")
    List<DailyCheckin> search(Date date);

    @Query(value = "SELECT j FROM DailyCheckin j")
    List<DailyCheckin> findAll();

}
