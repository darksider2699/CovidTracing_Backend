package com.example.backend.service;

import com.example.backend.payload.request.user.EditCompanyUserRequest;
import com.example.backend.payload.request.user.EditUserRequest;
import org.springframework.http.ResponseEntity;

public interface CompanyUserService {
    ResponseEntity<?> editUser(EditCompanyUserRequest editUserRequest, Long id);
}
