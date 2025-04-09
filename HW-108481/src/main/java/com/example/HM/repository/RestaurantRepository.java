package com.example.HM.repository;
import com.example.HM.model.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    
}
