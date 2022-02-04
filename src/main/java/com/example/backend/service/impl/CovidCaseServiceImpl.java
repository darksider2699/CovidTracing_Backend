package com.example.backend.service.impl;

import com.example.backend.models.medical_information.DailyCheckout;
import com.example.backend.models.user.MedicalUserInformation;
import com.example.backend.payload.request.medical.CovidCaseRequest;
import com.example.backend.repository.CovidCaseRepository;
import com.example.backend.repository.DailyCheckoutRepository;
import com.example.backend.repository.MedicalUserRepository;
import com.example.backend.service.CovidCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("covidCaseService")
public class CovidCaseServiceImpl implements CovidCaseService {
    @Autowired
    CovidCaseRepository covidCaseRepository;
    @Autowired
    MedicalUserRepository medicalUserRepository;
    @Autowired
    DailyCheckoutRepository dailyCheckoutRepository;


    @Override
    public ResponseEntity<?> addCovidCase(CovidCaseRequest covidCaseRequest) {
        Optional<MedicalUserInformation> optUser = medicalUserRepository.findById(covidCaseRequest.getId());
        if (!optUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<DailyCheckout> dailyCheckoutOfUser = dailyCheckoutRepository.findAllByMedicalUserInformation(optUser.get());
        Set<MedicalUserInformation> patientContact = new HashSet<>();
        for (DailyCheckout dailyCheckout: dailyCheckoutOfUser
             ) {
            patientContact.addAll(dailyCheckout.getContact());
        }

        return null;
    }
}
