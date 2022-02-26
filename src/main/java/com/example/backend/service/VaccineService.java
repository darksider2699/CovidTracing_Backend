package com.example.backend.service;

import com.example.backend.payload.request.medical.VaccineRequest;
import org.springframework.http.ResponseEntity;

public interface VaccineService {
    ResponseEntity<?> addNewVaccineShot(VaccineRequest vaccineRequest, Long id);
    ResponseEntity<?> getAllVaccineType();
    ResponseEntity<?> deleteVaccineShot(Long id);



}
