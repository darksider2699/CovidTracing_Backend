package com.example.backend.controller;

import com.example.backend.models.medical_information.DailyCheckin;
import com.example.backend.payload.request.dailycheckin.SearchDailyCheckinRequest;
import com.example.backend.payload.request.user.EditCompanyUserRequest;
import com.example.backend.payload.request.user.EditUserRequest;
import com.example.backend.service.impl.CompanyUserServiceImpl;
import com.example.backend.service.impl.DailyCheckinServiceImpl;
import com.example.backend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/daily_checkin")
public class DailyCheckinController {
    @Autowired
    DailyCheckinServiceImpl dailyCheckinService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public List<DailyCheckin> findAllDailyCheckin() {
        return dailyCheckinService.findAll();
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public List<DailyCheckin> search(@RequestBody SearchDailyCheckinRequest searchDailyCheckinRequest) {
        return dailyCheckinService.search(searchDailyCheckinRequest.getDateRecord());
    }
}
