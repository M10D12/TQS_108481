package com.example.HM.unit;

import com.example.HM.model.Meal;
import com.example.HM.model.Reservation;
import com.example.HM.repository.MealRepository;
import com.example.HM.repository.ReservationRepository;
import com.example.HM.service.ReservationService;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Test
    public void testCreateReservation() {
        MealRepository mealRepo = mock(MealRepository.class);
        ReservationRepository reservationRepo = mock(ReservationRepository.class);

        Meal meal = new Meal(1L, "Pizza", LocalDate.now().plusDays(1), null);
        Reservation fakeRes = new Reservation(meal);

        when(mealRepo.findById(1L)).thenReturn(Optional.of(meal));
        when(reservationRepo.save(any(Reservation.class))).thenReturn(fakeRes); 

        ReservationService service = new ReservationService(mealRepo, reservationRepo);
        Reservation r = service.createReservation(1L);

        assertEquals(meal.getDate(), r.getDate());
        assertNotNull(r.getToken());
        verify(reservationRepo, times(1)).save(any(Reservation.class));
    }


    @Test
    public void testCheckInSuccess() {
        ReservationRepository reservationRepo = mock(ReservationRepository.class);
        MealRepository mealRepo = mock(MealRepository.class);

        Reservation res = new Reservation();
        res.setUsed(false);
        res.setToken("abc123");

        when(reservationRepo.findByToken("abc123")).thenReturn(Optional.of(res));

        ReservationService service = new ReservationService(mealRepo, reservationRepo);
        boolean success = service.checkInReservation("abc123");

        assertTrue(success);
        assertTrue(res.isUsed());
        verify(reservationRepo).save(res);
    }

    @Test
    public void testCheckInFail_AlreadyUsed() {
        ReservationRepository reservationRepo = mock(ReservationRepository.class);
        MealRepository mealRepo = mock(MealRepository.class);

        Reservation res = new Reservation();
        res.setUsed(true);
        res.setToken("abc123");

        when(reservationRepo.findByToken("abc123")).thenReturn(Optional.of(res));

        ReservationService service = new ReservationService(mealRepo, reservationRepo);
        boolean success = service.checkInReservation("abc123");

        assertFalse(success);
        verify(reservationRepo, never()).save(any());
    }
}
