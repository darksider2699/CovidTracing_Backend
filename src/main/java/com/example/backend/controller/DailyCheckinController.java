package com.example.backend.controller;

import com.example.backend.models.medical_information.DailyCheckin;
import com.example.backend.payload.request.dailycheckin.SearchDailyCheckinRequest;
import com.example.backend.service.impl.DailyCheckinServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/user/daily_checkin")
public class DailyCheckinController {
    @Autowired
    DailyCheckinServiceImpl dailyCheckinService;

    @Transactional
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public List<DailyCheckin> findAllDailyCheckin() {
        return dailyCheckinService.findAll();
    }

    @Transactional
    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public List<DailyCheckin> search(@RequestBody SearchDailyCheckinRequest searchDailyCheckinRequest) {
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dailyCheckinService.search(ZonedDateTime.parse(searchDailyCheckinRequest.getDateRecord().toString(), DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US)).format(dt));
    }
}
