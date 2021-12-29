package com.example.backend.repository;

import com.example.backend.models.job_title.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {
    @Query("SELECT j FROM JobTitle j WHERE j.name = ?1 AND j.level = ?2")
    Optional<JobTitle> search(String name, int level);
}
