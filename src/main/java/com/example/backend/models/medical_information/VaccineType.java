package com.example.backend.models.medical_information;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table (name = "vaccineType")
@Getter
@Setter
public class VaccineType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EVaccineType name;
}
