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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        LocalDate dateRequest = addDailyCheckinRequest.getDateRecord().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate today = new Date ().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (dateRequest.isAfter(today)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<MedicalUserInformation> optUser = medicalUserRepository.findById(id);
        if (!optUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DailyCheckin dailyCheckin = new DailyCheckin(addDailyCheckinRequest.getDateRecord(), optUser.get(), addDailyCheckinRequest.getIsComing(), addDailyCheckinRequest.getIsAllowToCome());
        MedicalUserInformation user = optUser.get();
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
                temp = new TestResult(testResultRequest.getDateRecord(),
                        user,
                        testResultRequest.getIsPositive());
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

        }

        medicalUserRepository.save(medicalUserInformation);

        return ResponseEntity.ok(new MessageResponse("Medical information has just been updated! "));


    }

    @Override
    public ResponseEntity getAllDailyCheckinMedicalUserInformation() {
        List<MedicalUserInformation> medicalUserInformationList = medicalUserRepository.findAll();
        Integer numberOfUser = medicalUserInformationList.size();
        List<MedicalUserInformation> checkedInNumber = new ArrayList<>();
        for (int i = 0; i <= medicalUserInformationList.size() - 1; i++) {
            List<DailyCheckin> list = new ArrayList<>();
            for (DailyCheckin dailyCheckin : medicalUserInformationList.get(i).getDailyCheckinInformationList()) {
                list.add(dailyCheckin);
            }
            if (!list.isEmpty()) {
                list.sort(Comparator.comparing(DailyCheckin::getDateRecord).reversed());
                Calendar lastCheckin = Calendar.getInstance();
                lastCheckin.setTime(list.get(0).getDateRecord());
                Calendar today = Calendar.getInstance();
                if (today.get(Calendar.DATE) == lastCheckin.get(Calendar.DATE) && today.get(Calendar.MONTH) == lastCheckin.get(Calendar.MONTH) && today.get(Calendar.YEAR) == lastCheckin.get(Calendar.YEAR)) {
                    checkedInNumber.add(medicalUserInformationList.get(i));
                }
            }
        }

        Calendar cal = Calendar.getInstance();
        //cal.setTime(new Date());//Set specific Date if you want to
        List<Date> dateOfWeek = new ArrayList<>();
        List<Float> percentageCheckinOfWeek = new ArrayList<>();
        for (int i = Calendar.MONDAY; i <= Calendar.FRIDAY; i++) {
            cal.set(Calendar.DAY_OF_WEEK, i);
            dateOfWeek.add(cal.getTime());//Returns Date
        }
        for (int i = 0; i <= dateOfWeek.size() - 1; i++) {
            LocalDate localDate = dateOfWeek.get(i).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            List<DailyCheckin> listCheckinThatday = new ArrayList<>(dailyCheckinRepository.search(localDate));
            Integer userCheckedInAmount = listCheckinThatday.size();
            Float percentage = Float.valueOf(userCheckedInAmount) / Float.valueOf(numberOfUser ) * 100;
            percentageCheckinOfWeek.add(percentage);
        }


        return new ResponseEntity<>(
                new GetAllMedicalUserInformationResponse(numberOfUser, checkedInNumber.size(), medicalUserInformationList, percentageCheckinOfWeek),
                HttpStatus.OK);
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
            medicalUserRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("Daily Checkout has just been added successfully! "));

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> getMedicalUserById(Long id) {
        Optional<MedicalUserInformation> optUser = medicalUserRepository.findById(id);
        if (!optUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(medicalUserRepository.findById(id), HttpStatus.OK);
    }

}
