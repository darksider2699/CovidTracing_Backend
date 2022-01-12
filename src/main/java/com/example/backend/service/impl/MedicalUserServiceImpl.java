package com.example.backend.service.impl;

import com.example.backend.models.medical_information.*;
import com.example.backend.models.user.MedicalUserInformation;
import com.example.backend.payload.request.medical.AddDailyCheckinRequest;
import com.example.backend.payload.request.medical.AddDailyCheckoutRequest;
import com.example.backend.payload.request.medical.TestResultRequest;
import com.example.backend.payload.request.user.MedicalUserRequest;
import com.example.backend.payload.response.GetAllMedicalUserInformationResponse;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.*;
import com.example.backend.service.MedicalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("medicalUserService")

public class MedicalUserServiceImpl implements MedicalUserService {
    @Autowired
    MedicalUserRepository medicalUserRepository;
    @Autowired
    DailyCheckinRepository dailyCheckinRepository;
    @Autowired
    TestResultRepository testResultRepository;
    @Autowired
    VaccineRepository vaccineRepository;
    @Autowired
    DailyCheckoutRepository dailyCheckoutRepository;

    @Override
    public ResponseEntity<?> addDailyCheckin(AddDailyCheckinRequest addDailyCheckinRequest, Long id) {
        Optional<MedicalUserInformation> optUser = medicalUserRepository.findById(id);
        if (!optUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DailyCheckin dailyCheckin = new DailyCheckin(addDailyCheckinRequest.getDateRecord(), optUser.get(), addDailyCheckinRequest.isComing(), addDailyCheckinRequest.isAllowToCome());
        dailyCheckinRepository.save(dailyCheckin);

        MedicalUserInformation user = optUser.get();

        if (user.getLastCheckin() == null || user.getLastCheckin().getDateRecord().before(addDailyCheckinRequest.getDateRecord())) {
            DailyCheckin tempDailyCheckin = new DailyCheckin(addDailyCheckinRequest.getDateRecord(), user, addDailyCheckinRequest.isComing(), addDailyCheckinRequest.isAllowToCome());
            user.setLastCheckin(tempDailyCheckin);
        }

        Set<DailyCheckin> userCheckinHistory = user.getDailyCheckinInformationList();
        userCheckinHistory.add(dailyCheckin);
        medicalUserRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Daily Checkin has just been added successfully! "));
    }

    @Override
    public ResponseEntity<?> addListTestResult(List<TestResultRequest> testResultRequests) {
        Set<TestResult> testResults = new HashSet<>();
        for (TestResultRequest testResultRequest : testResultRequests
        ) {
            Optional<MedicalUserInformation> optUser = medicalUserRepository.findById(testResultRequest.getIdUser());
            if (!optUser.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                MedicalUserInformation user = optUser.get();
                TestResult temp;
                if (user.getLastCovidTest() == null || user.getLastCovidTest().before(testResultRequest.getDateRecord())) {
                    user.setLastCovidTest(testResultRequest.getDateRecord());
                }
                temp = new TestResult(testResultRequest.getDateRecord(),
                        user,
                        testResultRequest.isPositive());
                testResults.add(temp);

            }
            testResultRepository.saveAllAndFlush(testResults);
        }
        return ResponseEntity.ok(new MessageResponse("Daily Checkin has just been added successfully! "));
    }

    @Override
    public ResponseEntity<?> editUser(MedicalUserRequest editUserRequest, Long id) {
        Optional<MedicalUserInformation> optUser = medicalUserRepository.findById(id);

        if (!optUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MedicalUserInformation medicalUserInformation = optUser.get();

        if (editUserRequest.getStatus() != null && editUserRequest.getVaccineRequest() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

        if (editUserRequest.getStatus() != null) {
            medicalUserInformation.setStatus(editUserRequest.getStatus());
        }

        if (editUserRequest.getVaccineRequest() != null) {

            VaccineInformation vaccineInformation = new VaccineInformation(editUserRequest.getVaccineRequest().getDate(), EVaccineType.valueOf(editUserRequest.getVaccineRequest().getType()), medicalUserInformation);
            vaccineRepository.save(vaccineInformation);
            if (medicalUserInformation.getLastVaccinatedShot() == null || medicalUserInformation.getLastVaccinatedShot().before(editUserRequest.getVaccineRequest().getDate())) {
                medicalUserInformation.setLastVaccinatedShot(editUserRequest.getVaccineRequest().getDate());

            }
            if (medicalUserInformation.getVaccinatedStatus() == null) {
                medicalUserInformation.setVaccinatedStatus(1);
            } else {
                medicalUserInformation.setVaccinatedStatus(medicalUserInformation.getVaccinatedStatus() + 1);
            }
        }

        medicalUserRepository.save(medicalUserInformation);

        return ResponseEntity.ok(new MessageResponse("Medical information has just been updated! "));


    }

    @Override
    public GetAllMedicalUserInformationResponse getAllmedicalUserInformation() {
        List<MedicalUserInformation> medicalUserInformationList = medicalUserRepository.findAll();
        Integer numberOfUser = medicalUserInformationList.size();
        Integer checkedInNumber = (int) medicalUserInformationList.stream().filter(index -> {
            Calendar lastCheckin = Calendar.getInstance();
            if (index.getLastCheckin() != null) {
                lastCheckin.setTime(index.getLastCheckin().getDateRecord());
                Calendar cal = Calendar.getInstance();
                return lastCheckin.get(Calendar.DATE) == cal.get(Calendar.DATE) && lastCheckin.get(Calendar.MONTH) == cal.get(Calendar.MONTH) && lastCheckin.get(Calendar.YEAR) == cal.get(Calendar.YEAR);
            } else {
                return false;
            }
        }).count();

        return new GetAllMedicalUserInformationResponse(numberOfUser, checkedInNumber, medicalUserInformationList);
    }

    @Override
    @Transactional
    public ResponseEntity<?> addDailyCheckout(AddDailyCheckoutRequest addDailyCheckoutRequest, Long id) {

        List<MedicalUserInformation> medicalUserInformationList = medicalUserRepository.findByIdIn(addDailyCheckoutRequest.getListIdContactToday());
        Optional<MedicalUserInformation> optUser = medicalUserRepository.findById(id);
        if (optUser.isPresent()) {
            MedicalUserInformation user = optUser.get();
            DailyCheckout dailyCheckoutToday = new DailyCheckout();
            dailyCheckoutToday.setDateRecord(addDailyCheckoutRequest.getDateRecord());
            dailyCheckoutToday.setMedicalUserInformation(user);
            dailyCheckoutToday.setContact(medicalUserInformationList);
            dailyCheckoutRepository.save(dailyCheckoutToday);
            Set<DailyCheckout> userCheckoutHistory = user.getDailyCheckouts();
            userCheckoutHistory.add(dailyCheckoutToday);
            user.setDailyCheckouts(userCheckoutHistory);
            if (user.getLastCheckout() == null || user.getLastCheckout().getDateRecord().before(addDailyCheckoutRequest.getDateRecord())) {
                user.setLastCheckout(dailyCheckoutToday);
            }
            medicalUserRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("Daily Checkout has just been added successfully! "));

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
