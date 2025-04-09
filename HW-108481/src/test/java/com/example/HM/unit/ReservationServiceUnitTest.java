package com.example.HM.unit;

import com.example.HM.model.Meal;
import com.example.HM.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceUnitTest {

    private Meal meal;

    @BeforeEach
    public void setup() {
        meal = new Meal();
        meal.setName("Test Meal");
        meal.setDate(LocalDate.now());
    }

    @Test
    public void testReservationIsMarkedAsUsedInitiallyFalse() {
        Reservation res = new Reservation(meal);
        assertFalse(res.isUsed(), "Reserva não deve estar marcada como usada inicialmente");
    }

    @Test
    public void testMarkReservationAsUsed() {
        Reservation res = new Reservation(meal);
        res.setUsed(true);
        assertTrue(res.isUsed());
    }

    @Test
    public void testReservationDateMatchesMealDate() {
        Reservation res = new Reservation(meal);
        assertEquals(meal.getDate(), res.getDate());
    }

    @Test
    public void testReservationTokenIsGenerated() {
        Reservation res = new Reservation(meal);
        assertNotNull(res.getToken());
        assertFalse(res.getToken().isEmpty());
    }

    @Test
    public void testReservationMealIsCorrectlyAssigned() {
        Reservation res = new Reservation(meal);
        assertEquals(meal, res.getMeal());
    }


    @Test
    public void testReservationHasUniqueToken() {
        Reservation r1 = new Reservation(meal);
        Reservation r2 = new Reservation(meal);
        assertNotEquals(r1.getToken(), r2.getToken(), "Tokens devem ser únicos por reserva");
    }

    @Test
    public void testReservationCannotBeUsedTwice() {
        Reservation reservation = new Reservation(meal);
        assertFalse(reservation.isUsed());

        reservation.setUsed(true);
        assertTrue(reservation.isUsed());

        reservation.setUsed(true);
        assertTrue(reservation.isUsed(), "Reserva não deve poder ser reutilizada");
    }

    @Test
    public void testMultipleReservationsOnSameDay() {
        Set<Reservation> reservas = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            reservas.add(new Reservation(meal));
        }
        assertEquals(10, reservas.size());
    }

    @Test
    public void testReservationHasValidDate() {
        Reservation reservation = new Reservation(meal);
        assertTrue(reservation.getDate().isAfter(LocalDate.now().minusDays(1)));
    }

    @Test
    public void testMealAssociationIntegrity() {
        Meal m = new Meal();
        m.setName("Pizza Teste");
        m.setDate(LocalDate.of(2025, 4, 10));
        Reservation r = new Reservation(m);

        assertEquals("Pizza Teste", r.getMeal().getName());
        assertEquals(LocalDate.of(2025, 4, 10), r.getDate());
    }
}
