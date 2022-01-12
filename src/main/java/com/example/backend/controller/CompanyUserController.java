package com.example.backend.controller;

import com.example.backend.payload.request.user.EditCompanyUserRequest;
import com.example.backend.service.impl.CompanyUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user/company_user")
public class CompanyUserController {
    @Autowired
    CompanyUserServiceImpl userService;

    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> editUser(@RequestBody EditCompanyUserRequest editUserRequest, @PathVariable Long id) {
        return userService.editUser(editUserRequest, id);
    }
}
