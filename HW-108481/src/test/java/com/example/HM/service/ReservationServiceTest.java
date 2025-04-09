package com.example.HM.service;

import com.example.HM.model.Meal;
import com.example.HM.model.Reservation;
import com.example.HM.repository.MealRepository;
import com.example.HM.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    private ReservationRepository reservationRepository;
    private MealRepository mealRepository;
    private ReservationService reservationService;

    @BeforeEach
    public void setup() {
        reservationRepository = mock(ReservationRepository.class);
        mealRepository = mock(MealRepository.class);
        reservationService = new ReservationService(mealRepository, reservationRepository);

        // Retorna a própria reserva salva
        when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testTokenIsUniqueForEachReservation() {
        Meal meal = new Meal(1L, "Feijoada", LocalDate.now().plusDays(1), null);
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));

        Reservation r1 = reservationService.createReservation(1L);
        Reservation r2 = reservationService.createReservation(1L);

        assertNotEquals(r1.getToken(), r2.getToken(), "Cada token de reserva deve ser único");
    }

    @Test
    public void testCannotReuseTokenForCheckIn() {
        Meal meal = new Meal(1L, "Frango", LocalDate.now().plusDays(1), null);
        Reservation reservation = new Reservation(meal);
        reservation.setUsed(true);

        when(reservationRepository.findAll()).thenReturn(List.of(reservation));

        boolean result = reservationService.checkInReservation(reservation.getToken());
        assertFalse(result, "Reserva já usada não deve permitir novo check-in");
    }

    @Test
    public void testMultipleReservationsForSameMealDifferentTokens() {
        Meal meal = new Meal(1L, "Pizza", LocalDate.now().plusDays(1), null);
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));

        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Reservation res = reservationService.createReservation(1L);
            assertNotNull(res);
            tokens.add(res.getToken());
        }

        assertEquals(10, tokens.size());
        assertEquals(10, new HashSet<>(tokens).size(), "Tokens devem ser únicos mesmo para a mesma refeição");
    }

    @Test
    public void testCannotCreateReservationForPastMeal() {
        Meal oldMeal = new Meal(1L, "Sopa", LocalDate.now().minusDays(2), null);
        when(mealRepository.findById(1L)).thenReturn(Optional.of(oldMeal));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
            () -> reservationService.createReservation(1L),
            "Não deve ser possível reservar refeições de datas anteriores"
        );

        assertTrue(thrown.getMessage().toLowerCase().contains("passado"));
    }

    @Test
    public void testReservationAssociatesCorrectMeal() {
        Meal meal = new Meal(2L, "Lasanha", LocalDate.now().plusDays(3), null);
        when(mealRepository.findById(2L)).thenReturn(Optional.of(meal));

        Reservation reservation = reservationService.createReservation(2L);

        assertNotNull(reservation.getMeal(), "Reserva deve estar associada a uma refeição");
        assertEquals("Lasanha", reservation.getMeal().getName());
    }
}
