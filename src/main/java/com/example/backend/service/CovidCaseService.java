package com.example.backend.service;

import com.example.backend.payload.request.medical.CovidCaseRequest;
import org.springframework.http.ResponseEntity;

public interface CovidCaseService {
    ResponseEntity<?> addCovidCase(CovidCaseRequest covidCaseRequest);
}
