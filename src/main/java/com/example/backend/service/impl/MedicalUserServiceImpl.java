package com.example.backend.service.impl;

import com.example.backend.models.medical_information.DailyCheckin;
import com.example.backend.models.medical_information.TestResult;
import com.example.backend.models.user.MedicalUserInformation;
import com.example.backend.payload.request.medical.AddDailyCheckinRequest;
import com.example.backend.payload.request.medical.TestResultRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.DailyCheckinRepository;
import com.example.backend.repository.MedicalUserRepository;
import com.example.backend.repository.TestResultRepository;
import com.example.backend.service.MedicalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("medicalUserService")

public class MedicalUserServiceImpl implements MedicalUserService {
    @Autowired
    MedicalUserRepository medicalUserRepository;
    @Autowired
    DailyCheckinRepository dailyCheckinRepository;
    @Autowired
    TestResultRepository testResultRepository;

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

    @Override
    public ResponseEntity<?> addListTestResult(List<TestResultRequest> testResultRequests) {
       Set<TestResult> testResults = new HashSet<>();
        for (TestResultRequest testResultRequest: testResultRequests
             ) {
            Optional<MedicalUserInformation> optUser = medicalUserRepository.findById(testResultRequest.getIdUser());
            if (!optUser.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                TestResult temp;
                temp = new TestResult(testResultRequest.getDateRecord(),
                        optUser.get(),
                        testResultRequest.isPositive());
                testResults.add(temp);
            }
            testResultRepository.saveAllAndFlush(testResults);
        }
        return ResponseEntity.ok(new MessageResponse("Daily Checkin has just been added successfully! "));
    }


}
