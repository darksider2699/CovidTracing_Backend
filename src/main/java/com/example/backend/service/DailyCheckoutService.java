package com.example.backend.service;

import com.example.backend.models.medical_information.DailyCheckout;
import com.example.backend.payload.request.medical.AddDailyCheckoutRequest;
import com.example.backend.payload.request.user.MedicalUserRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DailyCheckoutService {
    List<DailyCheckout> findAll();
    List<DailyCheckout> search(String date);
    ResponseEntity<?> editDailyCheckout(AddDailyCheckoutRequest addDailyCheckoutRequest, Long id);

}
