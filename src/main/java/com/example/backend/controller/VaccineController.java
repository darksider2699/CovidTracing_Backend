package com.example.backend.controller;

import com.example.backend.payload.request.medical.VaccineRequest;
import com.example.backend.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/medical_user/vaccine")
public class VaccineController {
    @Autowired
    VaccineService vaccineService;

    @Transactional
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> addNewVaccineShot(@RequestBody VaccineRequest vaccineRequest, @PathVariable Long id) {
        return vaccineService.addNewVaccineShot(vaccineRequest, id);
    }
    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> deleteVaccineShot(@PathVariable Long id) {
        return vaccineService.deleteVaccineShot(id);
    }
    @Transactional
    @GetMapping("/vaccine_type/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> getAllVaccineType() {
        return vaccineService.getAllVaccineType();
    }
}
