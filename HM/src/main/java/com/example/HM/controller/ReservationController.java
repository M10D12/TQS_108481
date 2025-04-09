package com.example.HM.controller;

import com.example.HM.model.Reservation;
import com.example.HM.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestParam Long mealId) {
        return ResponseEntity.ok(reservationService.createReservation(mealId));
    }

    @GetMapping("/{token}")
    public ResponseEntity<Reservation> get(@PathVariable String token) {
        Optional<Reservation> res = reservationService.getByToken(token);
        return res.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{token}/checkin")
    public ResponseEntity<String> checkin(@PathVariable String token) {
        boolean success = reservationService.checkInReservation(token);
        if (success) return ResponseEntity.ok("Check-in efetuado com sucesso");
        return ResponseEntity.badRequest().body("Token inválido ou já usado");
    }

    @DeleteMapping("/{token}/cancel")
    public ResponseEntity<String> cancel(@PathVariable String token) {
        boolean success = reservationService.cancelReservation(token);
        if (success) return ResponseEntity.ok("Reserva cancelada com sucesso.");
        return ResponseEntity.badRequest().body("Reserva não encontrada ou já foi usada.");
    }

}
