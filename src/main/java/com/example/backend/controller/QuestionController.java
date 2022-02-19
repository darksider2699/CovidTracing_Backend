package com.example.backend.controller;

import com.example.backend.payload.request.question_answer.QuestionRequest;
import com.example.backend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

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
    public ResponseEntity<?> getAll() {
        return questionService.findAll();
    }

    @Transactional
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return questionService.findById(id);
    }


    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> updateAnswer(@RequestBody QuestionRequest updateAnswerOfQuestionRequest, @PathVariable Long id) {
        return questionService.updateQuestion(updateAnswerOfQuestionRequest, id);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> deleteAnswer(@PathVariable Long id) {
        return questionService.deleteQuestion(id);
    }
}
