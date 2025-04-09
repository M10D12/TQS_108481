package com.example.HM.integration;

import com.example.HM.model.Meal;
import com.example.HM.model.Reservation;
import com.example.HM.service.ReservationService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class ReservationApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    void testCreateReservation() throws Exception {
        Meal meal = new Meal(1L, "Feijoada", LocalDate.now().plusDays(1), null);
        Reservation reservation = new Reservation(meal);
        reservation.setToken("abc123");

        when(reservationService.createReservation(1L)).thenReturn(reservation);

        mockMvc.perform(post("/api/reservations").param("mealId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("abc123"));
    }
}
