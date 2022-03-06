package com.example.backend.service.impl;

import com.example.backend.models.Account;
import com.example.backend.payload.request.account.EditAccountRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.RoleRepository;
import com.example.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public ResponseEntity<?> changePassword(EditAccountRequest editAccountRequest, Long id) {
        accountRepository.findById(id).map(index -> {
            index.setPassword(encoder.encode(editAccountRequest.getNewPassword()));
            return accountRepository.save(index);
        }).orElseThrow(() -> new UsernameNotFoundException("There is no User"));

        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }

    @Override
    public ResponseEntity<?> deleteAccount(Long id) {
        Optional<Account> found = accountRepository.findById(id);

        if (!found.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountRepository.delete(found.get());
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllRole() {
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }
}


