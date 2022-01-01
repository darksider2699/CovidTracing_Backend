package com.example.backend.controller;

import com.example.backend.payload.request.medical.AddDailyCheckinRequest;
import com.example.backend.payload.request.medical.TestResultRequest;
import com.example.backend.service.impl.MedicalUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/medical_user")
public class MedicalUserController {
    @Autowired
    MedicalUserServiceImpl medicalUserService;

    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> addDailyCheckin(@RequestBody AddDailyCheckinRequest addDailyCheckinRequest, @PathVariable Long id) {
        return medicalUserService.addDailyCheckin(addDailyCheckinRequest, id);
    }

    @Transactional
    @PostMapping("/test_result")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> addListTestResult(@RequestBody List<TestResultRequest> testResultRequests) {
        return medicalUserService.addListTestResult(testResultRequests);
    }
}
