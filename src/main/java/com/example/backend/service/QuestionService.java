package com.example.backend.service;


import com.example.backend.payload.request.question_answer.QuestionRequest;
import org.springframework.http.ResponseEntity;


public interface QuestionService {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> addQuestion(QuestionRequest questionRequest);
    ResponseEntity<?> updateQuestion(QuestionRequest updateAnswerOfQuestionRequest, Long id);


}
