package com.example.backend.service;

import com.example.backend.payload.request.user.EditCompanyUserRequest;
import org.springframework.http.ResponseEntity;

public interface CompanyUserService {
    ResponseEntity<?> editUser(EditCompanyUserRequest editUserRequest, Long id);
    ResponseEntity<?> findAll();
}
