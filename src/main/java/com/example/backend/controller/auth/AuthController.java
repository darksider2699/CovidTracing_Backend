package com.example.backend.controller.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.backend.models.Account;
import com.example.backend.models.role.ERole;
import com.example.backend.models.role.Role;
import com.example.backend.models.user.CompanyUserInformation;
import com.example.backend.models.user.MedicalUserInformation;
import com.example.backend.models.user.User;
import com.example.backend.payload.request.auth.LoginRequest;
import com.example.backend.payload.request.auth.SignupRequest;
import com.example.backend.payload.response.JwtResponse;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.*;
import com.example.backend.security.jwt.JwtUtils;
import com.example.backend.security.service.AccountDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyUserRepository companyUserRepository;

    @Autowired
    MedicalUserRepository medicalUserRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        AccountDetailsImpl accountDetails = (AccountDetailsImpl) authentication.getPrincipal();
        List<String> roles = accountDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                accountDetails.getId(),
                accountDetails.getUsername(),
                roles));
    }
    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (Boolean.TRUE.equals(accountRepository.existsByUsername(signUpRequest.getUsername()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        // Create new user's account
        Account account = new Account(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()), new User());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        User user = new User();
        CompanyUserInformation companyUserInformation = new CompanyUserInformation();
        MedicalUserInformation medicalUserInformation = new MedicalUserInformation();

        user.setAccount(account);
        account.setUser(user);

        companyUserInformation.setUser(user);
        user.setCompanyUserInformation(companyUserInformation);

        medicalUserInformation.setUser(user);
        medicalUserInformation.setStatus(3);
        user.setMedicalUserInformation(medicalUserInformation);
        account.setRoles(roles);
        accountRepository.save(account);
        userRepository.save(user);
        companyUserRepository.save(companyUserInformation);
        medicalUserRepository.save(medicalUserInformation);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
