package com.example.backend.controller;

import com.example.backend.payload.request.medical.AddDailyCheckinRequest;
import com.example.backend.payload.request.user.EditCompanyUserRequest;
import com.example.backend.payload.request.user.EditUserRequest;
import com.example.backend.service.impl.CompanyUserServiceImpl;
import com.example.backend.service.impl.MedicalUserServiceImpl;
import com.example.backend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/medical_user")
public class MedicalUserController {
    @Autowired
    MedicalUserServiceImpl medicalUserService;

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> addDailyCheckin(@RequestBody AddDailyCheckinRequest addDailyCheckinRequest, @PathVariable Long id) {
        return medicalUserService.addDailyCheckin(addDailyCheckinRequest, id);
    }
}
