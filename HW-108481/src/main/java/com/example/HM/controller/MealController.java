package com.example.HM.controller;

import com.example.HM.dto.MealResponseDTO;
import com.example.HM.model.Meal;
import com.example.HM.service.MealService;
import com.example.HM.service.WeatherForecastService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;
    private final WeatherForecastService weatherService;

    public MealController(MealService mealService, WeatherForecastService weatherService) {
        this.mealService = mealService;
        this.weatherService = weatherService;
    }

    @GetMapping("/unique")
    public List<MealResponseDTO> getUniqueMeals(
            @RequestParam Long restaurantId,
            @RequestParam String date) {

        LocalDate localDate = LocalDate.parse(date.trim());

        List<Meal> meals = mealService.getMealsByRestaurantAndDate(restaurantId, localDate);

        if (meals.isEmpty()) {
            System.out.println("⚠️ Nenhuma refeição encontrada!");
        }

        Set<String> seen = new HashSet<>();

        return meals.stream()
                .filter(meal -> seen.add(meal.getName() + "|" + meal.getDate() + "|" + meal.getRestaurant().getName()))
                .map(meal -> new MealResponseDTO(meal, weatherService.getForecast(meal.getDate())))
                .collect(Collectors.toList());
    }

}
