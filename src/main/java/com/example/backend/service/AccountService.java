package com.example.backend.service;

import com.example.backend.models.Account;
import com.example.backend.payload.request.account.EditAccountRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    ResponseEntity<?> changePassword(EditAccountRequest editAccountRequest, Long id);
    ResponseEntity<?> deleteAccount(Long id);
    ResponseEntity<?> getAllRole();

}
