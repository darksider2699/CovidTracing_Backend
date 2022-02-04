package com.example.backend.repository;

import com.example.backend.models.medical_information.DailyCheckout;
import com.example.backend.models.user.MedicalUserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface DailyCheckoutRepository extends JpaRepository<DailyCheckout,Long>  {
    @Query(value = "SELECT j FROM DailyCheckout j  WHERE date_record between CONCAT(?1,' ','00:00:00') and CONCAT(?1,' ','23:59:59') ")
    List<DailyCheckout> search(LocalDate date);

    @Query(value = "SELECT j FROM DailyCheckout j")
    List<DailyCheckout> findAll();


    List<DailyCheckout> findAllByDateRecord(LocalDate date);
    List<DailyCheckout> findAllByMedicalUserInformation(MedicalUserInformation medicalUserInformation);
}
