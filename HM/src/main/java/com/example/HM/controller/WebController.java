package com.example.HM.controller;

import com.example.HM.dto.MealResponseDTO;
import com.example.HM.model.Meal;
import com.example.HM.model.Reservation;
import com.example.HM.model.Restaurant;
import com.example.HM.repository.ReservationRepository;
import com.example.HM.repository.RestaurantRepository;
import com.example.HM.repository.MealRepository;
import com.example.HM.service.WeatherForecastService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.HM.service.MealService;
import com.example.HM.service.ReservationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final MealRepository mealRepository;
    private final WeatherForecastService weatherService;
    private final RestaurantRepository restaurantRepository;
    private final MealService mealService;

    public WebController(ReservationRepository reservationRepository, ReservationService reservationService,MealRepository mealRepository, WeatherForecastService weatherService,MealService mealService, RestaurantRepository restaurantRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.mealRepository = mealRepository;
        this.weatherService = weatherService;
        this.mealService = mealService;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/reservas")
    public String mostrarReservas(Model model) {
        model.addAttribute("reservas", reservationRepository.findAll());
        return "reservations";
    }
    @PostMapping("/checkin/{token}")
    public String fazerCheckin(@PathVariable String token) {
        reservationService.checkInReservation(token);
        return "redirect:/reservas";
    }

    @GetMapping("/meals")
    public String mostrarRefeicoes(Model model) {
        Long restaurantId = 1L;

        List<Meal> meals = mealService.getMealsByRestaurant(restaurantId); 

        List<MealResponseDTO> mealsDTO = meals.stream()
                .map(meal -> new MealResponseDTO(meal, weatherService.getForecast(meal.getDate())))
                .toList();

        model.addAttribute("meals", mealsDTO);
        return "meals";
    }

    @PostMapping("/reservar")
    public String reservar(@RequestParam("mealId") Long mealId, Model model) {
        Reservation reserva = reservationService.createReservation(mealId);
        model.addAttribute("mensagem", "Reserva feita! Token: " + reserva.getToken());
        return "redirect:/reservas"; 
    }

    @GetMapping("/restaurants")
    public String listarRestaurantes(Model model) {
        model.addAttribute("restaurants", restaurantRepository.findAll());
        return "restaurants";
    }

}
