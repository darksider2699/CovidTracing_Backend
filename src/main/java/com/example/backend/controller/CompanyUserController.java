package com.example.backend.controller;

import com.example.backend.payload.request.user.EditCompanyUserRequest;
import com.example.backend.payload.request.user.EditUserRequest;
import com.example.backend.service.impl.CompanyUserServiceImpl;
import com.example.backend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/company_user")
public class CompanyUserController {
    @Autowired
    CompanyUserServiceImpl userService;

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> editUser(@RequestBody EditCompanyUserRequest editUserRequest, @PathVariable Long id) {
        return userService.editUser(editUserRequest, id);
    }
}
