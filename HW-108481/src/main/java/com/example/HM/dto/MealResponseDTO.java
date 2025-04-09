package com.example.HM.dto;

import com.example.HM.model.Meal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MealResponseDTO {
    private Meal meal;
    private String weather;
    public String date;
    public String restaurantName;
    public String name;


    public MealResponseDTO(Meal meal, String weather) {
        this.meal = meal;
        this.weather= weather;
        this.date = meal.getDate().toString();
        this.restaurantName = meal.getRestaurant().getName();
        this.name = meal.getName();
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getWeather() {
        return weather;
    }
    
}
