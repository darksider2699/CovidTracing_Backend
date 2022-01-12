package com.example.backend.service.impl;

import com.example.backend.models.medical_information.DailyCheckout;
import com.example.backend.models.user.MedicalUserInformation;
import com.example.backend.payload.request.medical.AddDailyCheckoutRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.DailyCheckoutRepository;
import com.example.backend.repository.MedicalUserRepository;
import com.example.backend.service.DailyCheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service("dailyCheckoutService")

public class DailyCheckoutServiceImpl implements DailyCheckoutService {
    @Autowired
    DailyCheckoutRepository dailyCheckoutRepository;
    @Autowired
    MedicalUserRepository medicalUserRepository;
    @Override
    public List<DailyCheckout> findAll() {
        return dailyCheckoutRepository.findAll();
    }

    @Override
    public List<DailyCheckout> search(String date) {
        LocalDate localDatePart = LocalDate.parse(date);
        return dailyCheckoutRepository.search(localDatePart);
    }

    @Override
    public ResponseEntity<?> editDailyCheckout(AddDailyCheckoutRequest addDailyCheckoutRequest, Long id) {
        List<Long> listContactNew  = addDailyCheckoutRequest.getListIdContactToday();
        Optional<DailyCheckout> optionalDailyCheckout = dailyCheckoutRepository.findById(id);
        List<MedicalUserInformation> listMedicalUserInformation = medicalUserRepository.findByIdIn(listContactNew);
        if (!optionalDailyCheckout.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Record not found"));
        }
       DailyCheckout result =  optionalDailyCheckout.get();
        result.setContact(listMedicalUserInformation);
        dailyCheckoutRepository.save(result);
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }
}
