package com.example.backend.service.impl;

import com.example.backend.models.medical_information.TestResult;
import com.example.backend.payload.response.TestResultCountResponse;
import com.example.backend.repository.TestResultRepository;
import com.example.backend.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service("testResultService")

public class TestResultServiceImpl implements TestResultService {
    @Autowired
    TestResultRepository testResultRepository;

    @Override
    public List<TestResult> findAll() {
        return testResultRepository.findAll();
    }

    @Override
    public List<TestResult> search(String date) {
        LocalDate localDatePart = LocalDate.parse(date);
        return testResultRepository.search(localDatePart);
    }

    @Override
    public TestResultCountResponse getGeneralResult(String date) {
        LocalDate localDatePart = LocalDate.parse(date);
        List<TestResult> results = testResultRepository.search(localDatePart);
        Long total = (long) results.size();
        Long numberOfNegative = (long) (int) results.stream().filter(index -> !index.isPositive()).count();
        return new TestResultCountResponse(total,numberOfNegative);
    }

}
