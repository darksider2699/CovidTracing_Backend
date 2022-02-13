package com.example.backend.service;

import org.springframework.http.ResponseEntity;


public interface TestResultService {
    ResponseEntity<?> findAll();
    ResponseEntity<?> search(String date);
    ResponseEntity<?> getGeneralResult(String date);
    ResponseEntity<?> getAllGeneralTestResult();
}
