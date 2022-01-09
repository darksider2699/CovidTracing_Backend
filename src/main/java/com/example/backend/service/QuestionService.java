package com.example.backend.service;


import com.example.backend.models.questions_answers.Question;
import com.example.backend.payload.request.question_answer.QuestionRequest;
import com.example.backend.payload.request.question_answer.UpdateAnswerOfQuestionRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {
    List<Question> findAll();
    ResponseEntity<?> addQuestion(QuestionRequest questionRequest);
    ResponseEntity<?> updateQuestion(UpdateAnswerOfQuestionRequest updateAnswerOfQuestionRequest, Long id);


}
