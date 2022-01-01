package com.example.backend.service;

import com.example.backend.models.medical_information.TestResult;

import java.util.List;

public interface TestResultService {
    List<TestResult> findAll();
    List<TestResult> search(String date);
}
