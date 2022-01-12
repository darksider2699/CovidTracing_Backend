package com.example.backend.repository;

import com.example.backend.models.user.MedicalUserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MedicalUserRepository extends JpaRepository<MedicalUserInformation,Long> {
    @Query(value = "SELECT m from MedicalUserInformation  m join DailyCheckin  d join User u WHERE CONCAT(u.firstName, ' ', u.lastName) LIKE %?1% ")
    public List<MedicalUserInformation> search (String name);

    public List<MedicalUserInformation> findByIdIn(List<Long> listId);
}
