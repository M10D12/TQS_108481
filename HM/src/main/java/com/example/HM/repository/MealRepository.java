package com.example.HM.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HM.model.Meal;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByRestaurantIdAndDate(Long restaurantId,LocalDate date);
    List<Meal> findByRestaurantId(Long restaurantId);

}
