package com.example.backend.service;

import com.example.backend.models.medical_information.TestResult;
import com.example.backend.payload.response.TestResultCountResponse;

import java.util.List;
import java.util.Set;

public interface TestResultService {
    List<TestResult> findAll();
    List<TestResult> search(String date);
    TestResultCountResponse getGeneralResult(String date);
    Set<TestResultCountResponse> getAllGeneralTestResult();
}
