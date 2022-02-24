package com.example.backend.controller;

import com.example.backend.payload.request.medical.AddDailyCheckinRequest;
import com.example.backend.payload.request.medical.AddDailyCheckoutRequest;
import com.example.backend.payload.request.medical.TestResultRequest;
import com.example.backend.payload.request.user.MedicalUserRequest;
import com.example.backend.service.MedicalUserService;
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
    MedicalUserService medicalUserService;

    @Transactional
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity getMedicalUserById(@PathVariable Long id) {
        return medicalUserService.getMedicalUserById(id);
    }

    @Transactional
    @PutMapping("/daily_checkin/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> addDailyCheckin(@RequestBody AddDailyCheckinRequest addDailyCheckinRequest, @PathVariable Long id) {
        return medicalUserService.addDailyCheckin(addDailyCheckinRequest, id);
    }

    @Transactional
    @GetMapping("/daily_checkin/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity getAll() {
        return medicalUserService.getAllDailyCheckinMedicalUserInformation();
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

    @Transactional
    @PutMapping("/daily_checkout/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> addDailyCheckout(@RequestBody AddDailyCheckoutRequest addDailyCheckoutRequest, @PathVariable Long id) {
        return medicalUserService.addDailyCheckout(addDailyCheckoutRequest, id);
    }
}
