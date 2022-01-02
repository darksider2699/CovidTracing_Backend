package com.example.backend.controller;

import com.example.backend.payload.request.medical.AddDailyCheckinRequest;
import com.example.backend.payload.request.medical.TestResultRequest;
import com.example.backend.payload.request.user.MedicalUserRequest;
import com.example.backend.service.impl.MedicalUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/medical_user")
public class MedicalUserController {
    @Autowired
    MedicalUserServiceImpl medicalUserService;

    @Transactional
    @PutMapping("/daily_checkin/{id}")
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

    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> editUser(@RequestBody MedicalUserRequest editUserRequest, @PathVariable Long id) {
        return medicalUserService.editUser(editUserRequest, id);
    }
}
