package com.example.backend.service;

import com.example.backend.models.medical_information.DailyCheckout;
import com.example.backend.payload.request.medical.AddDailyCheckoutRequest;
import com.example.backend.payload.request.user.MedicalUserRequest;
import org.springframework.http.ResponseEntity;


public interface DailyCheckoutService {
    ResponseEntity<?> findAll();
    ResponseEntity<?> search(String date);
    ResponseEntity<?> searchByDateAndIdUser(String date, Long id);
    ResponseEntity<?> editDailyCheckout(AddDailyCheckoutRequest addDailyCheckoutRequest, Long id);

}
