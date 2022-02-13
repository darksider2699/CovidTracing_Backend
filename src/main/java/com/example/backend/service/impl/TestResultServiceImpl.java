package com.example.backend.service.impl;

import com.example.backend.models.medical_information.TestResult;
import com.example.backend.payload.response.TestResultCountResponse;
import com.example.backend.repository.TestResultRepository;
import com.example.backend.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("testResultService")

public class TestResultServiceImpl implements TestResultService {
    @Autowired
    TestResultRepository testResultRepository;

    @Override
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(testResultRepository.findAll(),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> search(String date) {
        LocalDate localDatePart = LocalDate.parse(date);
        return new ResponseEntity<>(
                testResultRepository.search(localDatePart),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getGeneralResult(String date) {
        return new ResponseEntity<>(
                getGeneralTestResult(date),
                HttpStatus.OK);
    }

    public TestResultCountResponse getGeneralTestResult(String date) {
        LocalDate localDatePart = LocalDate.parse(date);
        List<TestResult> results = testResultRepository.search(localDatePart);
        Long total = (long) results.size();
        Long numberOfNegative = (long) (int) results.stream().filter(index -> !index.isPositive()).count();

        return new TestResultCountResponse(localDatePart, total, numberOfNegative);
    }

    @Override
    public ResponseEntity<?> getAllGeneralTestResult() {
        Set<TestResultCountResponse> result = new HashSet<>();
        List<TestResult> testResults = testResultRepository.findAll();
        for (TestResult testResult : testResults
        ) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            result.add(getGeneralTestResult(formatter.format(testResult.getDateRecord())));
        }
        return new ResponseEntity<>(result.stream().sorted(Comparator.comparing(TestResultCountResponse::getDateRecord)),
                HttpStatus.OK);
    }

}
