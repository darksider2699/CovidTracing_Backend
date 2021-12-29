package com.example.backend.repository;

import com.example.backend.models.questions_answers.Question;
import com.example.backend.models.user.MedicalUserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository  extends JpaRepository<Question,Long> {
}
