package com.example.backend.repository;

import com.example.backend.models.questions_answers.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository  extends JpaRepository<Answer,Long> {
}
