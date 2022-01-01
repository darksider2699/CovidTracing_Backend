package com.example.backend.service;

import com.example.backend.models.medical_information.DailyCheckin;

import java.util.List;
public interface DailyCheckinService {
    List<DailyCheckin> findAll();
    List<DailyCheckin> search(String date);
}
