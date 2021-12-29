package com.example.backend.service;

import com.example.backend.models.Account;
import com.example.backend.models.user.User;
import com.example.backend.payload.request.user.EditUserRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<?> editUser(EditUserRequest editUserRequest,  Long id);
    List<User> findAll();

}
