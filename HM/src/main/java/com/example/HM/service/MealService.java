package com.example.HM.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.HM.model.Meal;
import com.example.HM.repository.MealRepository;

@Service
public class MealService {
    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> getMealsByRestaurantAndDate(Long restaurantId,LocalDate date) {
        return mealRepository.findByRestaurantIdAndDate(restaurantId,date);
    }
    public List<Meal> getMealsByRestaurant(Long restaurantId) {
        return mealRepository.findByRestaurantId(restaurantId);
    }
    

   
}
