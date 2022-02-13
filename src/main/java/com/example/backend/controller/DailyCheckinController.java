package com.example.backend.controller;

import com.example.backend.payload.request.dailycheckin.SearchDailyCheckinRequest;
import com.example.backend.service.DailyCheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user/daily_checkin")
public class DailyCheckinController {
    @Autowired
    DailyCheckinService dailyCheckinService;

    @Transactional
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> findAllDailyCheckin() {
        return dailyCheckinService.findAll();
    }

    @Transactional
    @PostMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> search(@RequestBody SearchDailyCheckinRequest searchDailyCheckinRequest) {
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dailyCheckinService.search(ZonedDateTime.parse(searchDailyCheckinRequest.getDateRecord().toString(), DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US)).format(dt));
    }
}
