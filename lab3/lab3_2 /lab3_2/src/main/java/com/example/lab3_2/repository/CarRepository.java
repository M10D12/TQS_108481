package com.example.lab3_2.repository;

import java.util.List;

public interface CarRepository {
    List<Car> findAll();
    Car findByCarIdCar(Long carId);
}
