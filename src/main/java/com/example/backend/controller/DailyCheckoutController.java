package com.example.backend.controller;

import com.example.backend.models.medical_information.DailyCheckin;
import com.example.backend.models.medical_information.DailyCheckout;
import com.example.backend.payload.request.dailycheckin.SearchDailyCheckinRequest;
import com.example.backend.payload.request.medical.AddDailyCheckoutRequest;
import com.example.backend.repository.DailyCheckoutRepository;
import com.example.backend.service.DailyCheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user/daily_checkout")
public class DailyCheckoutController {
    @Autowired
    DailyCheckoutService dailyCheckoutService;

    @Transactional
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> findAllDailyCheckout() {
        return dailyCheckoutService.findAll();
    }


    @Transactional
    @PostMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> search(@RequestBody SearchDailyCheckinRequest searchDailyCheckinRequest) {
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dailyCheckoutService.search(ZonedDateTime.parse(searchDailyCheckinRequest.getDateRecord().toString(), DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US)).format(dt));
    }

    @Transactional
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> searchByDateAndIdUser(@RequestBody SearchDailyCheckinRequest searchDailyCheckinRequest, @PathVariable Long id) {
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dailyCheckoutService.searchByDateAndIdUser(ZonedDateTime.parse(searchDailyCheckinRequest.getDateRecord().toString(), DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US)).format(dt), id);
    }

    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus
    public ResponseEntity<?> updateDailyCheckout(@RequestBody AddDailyCheckoutRequest addDailyCheckoutRequest, @PathVariable Long id) {
        return dailyCheckoutService.editDailyCheckout(addDailyCheckoutRequest, id);
    }
}
