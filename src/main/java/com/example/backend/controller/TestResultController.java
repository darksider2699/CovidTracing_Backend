package com.example.backend.controller;

import com.example.backend.models.medical_information.TestResult;
import com.example.backend.payload.request.medical.TestResultRequest;
import com.example.backend.payload.response.TestResultCountResponse;
import com.example.backend.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/test_result")
public class TestResultController {
    @Autowired
    TestResultService testResultService;

    @Transactional
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public List<TestResult> findAllTestResult() {
        return testResultService.findAll();
    }

    @Transactional
    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public List<TestResult> search (@RequestBody TestResultRequest testResultRequest) {
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return testResultService.search(ZonedDateTime.parse(testResultRequest.getDateRecord().toString(), DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US)).format(dt));

    }

    @Transactional
    @PostMapping("/general_result")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public TestResultCountResponse getGeneralResult (@RequestBody TestResultRequest testResultRequest) {
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return testResultService.getGeneralResult(ZonedDateTime.parse(testResultRequest.getDateRecord().toString(), DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US)).format(dt));
    }
}
