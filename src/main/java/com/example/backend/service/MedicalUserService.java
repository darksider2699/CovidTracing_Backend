package com.example.backend.service;

import com.example.backend.payload.request.medical.AddDailyCheckinRequest;
import org.springframework.http.ResponseEntity;

public interface MedicalUserService {
    ResponseEntity<?> addDailyCheckin(AddDailyCheckinRequest addDailyCheckinRequest, Long id);
}
