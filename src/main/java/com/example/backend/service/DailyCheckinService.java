package com.example.backend.service;

import org.springframework.http.ResponseEntity;

public interface DailyCheckinService {
    ResponseEntity<?> findAll();
    ResponseEntity<?> search(String date);
}
