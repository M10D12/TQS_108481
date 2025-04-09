package com.example.HM.service;

import com.example.HM.model.Meal;
import com.example.HM.model.Reservation;
import com.example.HM.repository.MealRepository;
import com.example.HM.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {

    private final MealRepository mealRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(MealRepository mealRepo, ReservationRepository resRepo) {
        this.mealRepository = mealRepo;
        this.reservationRepository = resRepo;
    }

    public Reservation createReservation(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));
        Reservation reservation = new Reservation(meal);
        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> getByToken(String token) {
        return reservationRepository.findByToken(token);
    }

    public boolean cancelReservation(String token) {
        Optional<Reservation> reservation = reservationRepository.findByToken(token);
        if (reservation.isPresent() && !reservation.get().isUsed()) {
            reservationRepository.delete(reservation.get());
            return true;
        }
        return false;
    }
    

    public boolean checkInReservation(String token) {
        Optional<Reservation> optional = reservationRepository.findByToken(token);
        if (optional.isEmpty()) return false;

        Reservation reservation = optional.get();
        if (reservation.isUsed()) return false;

        reservation.setUsed(true);
        reservationRepository.save(reservation);
        return true;
    }
}
