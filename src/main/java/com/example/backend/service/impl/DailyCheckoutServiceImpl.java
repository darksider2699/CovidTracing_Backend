package com.example.backend.service.impl;

import com.example.backend.models.medical_information.DailyCheckout;
import com.example.backend.models.user.MedicalUserInformation;
import com.example.backend.payload.request.medical.AddDailyCheckoutRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.DailyCheckoutRepository;
import com.example.backend.repository.MedicalUserRepository;
import com.example.backend.service.DailyCheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(dailyCheckoutRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> search(String date) {
        LocalDate localDatePart = LocalDate.parse(date);
        return new ResponseEntity<>(dailyCheckoutRepository.search(localDatePart), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> searchByDateAndIdUser(String date, Long id) {
        LocalDate localDatePart = LocalDate.parse(date);
        return new ResponseEntity<>(dailyCheckoutRepository.searchByDateAndIdUser(localDatePart, id), HttpStatus.OK);
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
