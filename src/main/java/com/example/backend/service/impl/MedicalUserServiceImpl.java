package com.example.backend.service.impl;

import com.example.backend.models.medical_information.DailyCheckin;
import com.example.backend.models.user.MedicalUserInformation;
import com.example.backend.payload.request.medical.AddDailyCheckinRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.DailyCheckinRepository;
import com.example.backend.repository.MedicalUserRepository;
import com.example.backend.service.MedicalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service("medicalUserService")

public class MedicalUserServiceImpl implements MedicalUserService {
    @Autowired
    MedicalUserRepository medicalUserRepository;
    @Autowired
    DailyCheckinRepository dailyCheckinRepository;

    @Override
    public ResponseEntity<?> addDailyCheckin(AddDailyCheckinRequest addDailyCheckinRequest, Long id) {
        Optional<MedicalUserInformation> optUser = medicalUserRepository.findById(id);
        if (!optUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DailyCheckin dailyCheckin = new DailyCheckin(addDailyCheckinRequest.getDateRecord(), optUser.get(), addDailyCheckinRequest.isComing(), addDailyCheckinRequest.isAllowToCome());
        dailyCheckinRepository.save(dailyCheckin);

        MedicalUserInformation user = optUser.get();
        Set<DailyCheckin> userCheckinHistory = user.getDailyCheckinInformationList();
        userCheckinHistory.add(dailyCheckin);
        medicalUserRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Daily Checkin has just been added successfully! "));
    }
}
