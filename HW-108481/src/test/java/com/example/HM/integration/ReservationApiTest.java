package com.example.HM.integration;

import com.example.HM.controller.ReservationController;
import com.example.HM.model.Meal;
import com.example.HM.model.Reservation;
import com.example.HM.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
public class ReservationApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateReservation() throws Exception {
        Meal meal = new Meal(1L, "Pizza", LocalDate.now().plusDays(1), null);
        Reservation r = new Reservation(meal);
        r.setToken("abc123");

        Mockito.when(reservationService.createReservation(1L)).thenReturn(r);

        mockMvc.perform(post("/api/reservations?mealId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("abc123"));
    }

    @Test
    public void testGetReservationByToken() throws Exception {
        Reservation r = new Reservation(new Meal(1L, "Lasanha", LocalDate.now().plusDays(1), null));
        r.setToken("xyz789");

        Mockito.when(reservationService.getByToken("xyz789")).thenReturn(Optional.of(r));

        mockMvc.perform(get("/api/reservations/xyz789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("xyz789")));
    }

    @Test
    public void testReservationNotFound() throws Exception {
        Mockito.when(reservationService.getByToken("notfound")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/reservations/notfound"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSuccessfulCheckin() throws Exception {
        Mockito.when(reservationService.checkInReservation("abc123")).thenReturn(true);

        mockMvc.perform(post("/api/reservations/abc123/checkin"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Check-in efetuado com sucesso")));
    }

    @Test
    public void testCheckinFailsIfAlreadyUsed() throws Exception {
        Mockito.when(reservationService.checkInReservation("usedtoken")).thenReturn(false);

        mockMvc.perform(post("/api/reservations/usedtoken/checkin"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("inválido")));
    }
}
