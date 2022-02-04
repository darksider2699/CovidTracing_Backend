package com.example.backend.controller;

import com.example.backend.payload.request.medical.CovidCaseRequest;
import com.example.backend.service.CovidCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/covid_case")
public class CovidCaseController {
    @Autowired
    CovidCaseService covidCaseService;
    @Transactional
    @PostMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> addNewCase(@RequestBody CovidCaseRequest covidCaseRequest) {
        return covidCaseService.addCovidCase(covidCaseRequest);
    }
}
