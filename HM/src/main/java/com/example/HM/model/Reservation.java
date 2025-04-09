package com.example.HM.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

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

    public Reservation(Meal meal){
        this.meal = meal;
        this.date = meal.getDate();
        this.token=UUID.randomUUID().toString();
        this.used = false;
    }
}
