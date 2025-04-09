package com.example.HM;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.HM.model.Meal;
import com.example.HM.model.Restaurant;
import com.example.HM.repository.MealRepository;
import com.example.HM.repository.RestaurantRepository;

@SpringBootApplication
public class HmApplication {

	public static void main(String[] args) {
		SpringApplication.run(HmApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(RestaurantRepository restaurantRepo, MealRepository mealRepo) {
		return args -> {
			if (restaurantRepo.count() == 0 && mealRepo.count() == 0) {
				Restaurant r = new Restaurant(null, "Cantina Central", "Campus Sul");
				Restaurant l = new Restaurant(null, "Cantina Santiago", "Campus Centro");

				restaurantRepo.save(r);
				restaurantRepo.save(l);

				Meal m1 = new Meal(null, "Feijoada", LocalDate.of(2025,4,8), r);
				Meal m2 = new Meal(null, "Salada Frango", LocalDate.now().plusDays(1), r);
				Meal m3 = new Meal(null, "Francesinha", LocalDate.now(), r);
				Meal m4 = new Meal(null, "Pizza", LocalDate.now(), l);

				mealRepo.save(m1);
				mealRepo.save(m2);
				mealRepo.save(m3);
				mealRepo.save(m4);
			}
		};
	}



}
