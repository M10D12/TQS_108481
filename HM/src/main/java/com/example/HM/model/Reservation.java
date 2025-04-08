package com.example.HM.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDate date;

    private boolean used = false;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;
}
