package com.example.backend.service.impl;

import com.example.backend.models.medical_information.TestResult;
import com.example.backend.repository.TestResultRepository;
import com.example.backend.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("testResultService")

public class TestResultServiceImpl  implements TestResultService {
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

}
