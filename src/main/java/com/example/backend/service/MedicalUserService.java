package com.example.backend.service;

import com.example.backend.payload.request.medical.AddDailyCheckinRequest;
import com.example.backend.payload.request.medical.TestResultRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MedicalUserService {
    ResponseEntity<?> addDailyCheckin(AddDailyCheckinRequest addDailyCheckinRequest, Long id);
    ResponseEntity<?> addListTestResult(List<TestResultRequest> testResultRequest);
}
