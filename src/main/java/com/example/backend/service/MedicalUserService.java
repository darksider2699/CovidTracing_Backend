package com.example.backend.service;

import com.example.backend.payload.request.medical.AddDailyCheckinRequest;
import com.example.backend.payload.request.medical.AddDailyCheckoutRequest;
import com.example.backend.payload.request.medical.TestResultRequest;
import com.example.backend.payload.request.user.MedicalUserRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MedicalUserService {
    ResponseEntity<?> addDailyCheckin(AddDailyCheckinRequest addDailyCheckinRequest, Long id);
    ResponseEntity<?> addListTestResult(List<TestResultRequest> testResultRequest);
    ResponseEntity<?> editUser(MedicalUserRequest editUserRequest, Long id);
    ResponseEntity<?> getAllDailyCheckinMedicalUserInformation();
    ResponseEntity<?> addDailyCheckout(AddDailyCheckoutRequest addDailyCheckoutRequest, Long id);
    ResponseEntity<?> getMedicalUserById(Long id);
}
