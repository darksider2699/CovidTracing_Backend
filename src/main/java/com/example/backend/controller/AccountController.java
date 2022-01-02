package com.example.backend.controller;

import com.example.backend.models.Account;
import com.example.backend.payload.request.account.EditAccountRequest;
import com.example.backend.repository.AccountRepository;
import com.example.backend.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AccountServiceImpl accountService;
    @Transactional
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Account> getAllAccount() {
        return accountService.findAll();
    }

    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> changePassword(@RequestBody EditAccountRequest editAccountRequest, @PathVariable Long id) {
        return accountService.changePassword(editAccountRequest, id);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        return accountService.deleteAccount(id);
    }

    @Transactional
    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @Transactional
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
