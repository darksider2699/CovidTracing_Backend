package com.example.backend.controller;

import com.example.backend.payload.request.question_answer.QuestionRequest;
import com.example.backend.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    QuestionServiceImpl questionService;

    @PostMapping("/add_question")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> addQuestion(@RequestBody QuestionRequest questionRequest) {
       return  questionService.addQuestion(questionRequest);
    }
}