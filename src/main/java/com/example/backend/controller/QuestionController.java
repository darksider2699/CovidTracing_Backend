package com.example.backend.controller;

import com.example.backend.models.questions_answers.Question;
import com.example.backend.payload.request.question_answer.QuestionRequest;
import com.example.backend.payload.request.question_answer.UpdateAnswerOfQuestionRequest;
import com.example.backend.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    QuestionServiceImpl questionService;

    @Transactional
    @PostMapping("/add_question")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> addQuestion(@RequestBody QuestionRequest questionRequest) {
        return questionService.addQuestion(questionRequest);
    }

    @Transactional
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public List<Question> getAll() {
        return questionService.findAll();
    }

    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> updateAnswer(@RequestBody UpdateAnswerOfQuestionRequest updateAnswerOfQuestionRequest, @PathVariable Long id) {
        return questionService.updateQuestion(updateAnswerOfQuestionRequest, id);
    }
}
