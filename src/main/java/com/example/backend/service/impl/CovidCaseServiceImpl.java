package com.example.backend.service.impl;

import com.example.backend.models.medical_information.CovidCase;
import com.example.backend.models.medical_information.DailyCheckout;
import com.example.backend.models.user.MedicalUserInformation;
import com.example.backend.payload.request.medical.CovidCaseRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.CovidCaseRepository;
import com.example.backend.repository.DailyCheckoutRepository;
import com.example.backend.repository.MedicalUserRepository;
import com.example.backend.service.CovidCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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
        LocalDateTime now = covidCaseRequest.getDateRecord().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime then = now.minusDays(14);
        Optional<MedicalUserInformation> optUser = medicalUserRepository.findById(covidCaseRequest.getId());
        if (!optUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MedicalUserInformation user = optUser.get();
        List<DailyCheckout> dailyCheckoutOfUser = dailyCheckoutRepository.findAllByMedicalUserInformation(user);
        Set<MedicalUserInformation> patientContact = new HashSet<>();
        for (DailyCheckout dailyCheckout : dailyCheckoutOfUser
        ) {
            if (dailyCheckout.getDateRecord().after(Date.from(then.atZone(ZoneId.systemDefault())
                    .toInstant())) && dailyCheckout.getDateRecord().before(Date.from(now.plusDays(1).atZone(ZoneId.systemDefault())
                    .toInstant()))) {
                patientContact.addAll(dailyCheckout.getContact());
            }
        }
        user.setStatus(covidCaseRequest.getCovidStatus());
        for (MedicalUserInformation contact : patientContact) {
            if (contact.getStatus() > covidCaseRequest.getCovidStatus() + 1) {
                contact.setStatus(covidCaseRequest.getCovidStatus() + 1);
            }
        }
        List<CovidCase> allCases = covidCaseRepository.findAll();
        if (allCases.stream().anyMatch(index -> index.getPatient().getId().equals(covidCaseRequest.getId()))) {
            CovidCase existCase = allCases.stream().filter(index -> index.getPatient().getId() == covidCaseRequest.getId()).collect(Collectors.toList()).get(0);
            if (existCase.getDateRecord().before(covidCaseRequest.getDateRecord())) {
                existCase.setDateRecord(covidCaseRequest.getDateRecord());
                return ResponseEntity.ok(new MessageResponse("Covid case has just been updated! "));
            } else {
                return ResponseEntity.ok(new MessageResponse("This case is older than the latest! "));
            }
        } else {
            CovidCase covidCase = new CovidCase(user, new ArrayList<>(patientContact), covidCaseRequest.getDateRecord(), covidCaseRequest.getCovidStatus());
            covidCaseRepository.save(covidCase);
            return ResponseEntity.ok(new MessageResponse("Covid cases have just been updated! "));
        }
    }

    @Override
    public ResponseEntity<?> getAllCovidCases() {
        return new ResponseEntity<>(covidCaseRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Optional<CovidCase> optCase = covidCaseRepository.findById(id);
        if (!optCase.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        CovidCase covidCase = optCase.get();
        MedicalUserInformation patient = covidCase.getPatient();
        patient.setStatus(3);
        List<MedicalUserInformation> listContact = covidCase.getPatientContact();
        for (MedicalUserInformation user : listContact) {
            user.setStatus(3);
        }
        covidCaseRepository.delete(covidCase);
        updateCovidStatusForWholeUser();
        return ResponseEntity.ok(new MessageResponse("Covid case has just been deleted! "));
    }

    public void updateCovidStatusForWholeUser() {
        List<CovidCase> listCases = covidCaseRepository.findAll();
        for (CovidCase covidCase : listCases) {
            MedicalUserInformation patient = covidCase.getPatient();
            patient.setStatus(covidCase.getStatus());
            List<MedicalUserInformation> listContact = covidCase.getPatientContact();
            for (MedicalUserInformation contact : listContact) {
                if (contact.getStatus() > covidCase.getStatus() + 1) {
                    contact.setStatus(covidCase.getStatus() + 1);
                }
            }
        }
    }
}


