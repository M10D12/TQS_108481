package com.example.lab3_2;

import com.example.lab3_2.model.Car;
import com.example.lab3_2.service.CarManagerService;
import com.example.lab3_2.controller.CarController;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @MockBean
    private CarManagerService carService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void whenGetAllCars_thenReturnJsonArray() throws Exception {
        List<Car> cars = Arrays.asList(
            new Car("Toyota", "Corolla"),
            new Car("Honda", "Civic")
        );

        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/api/cars")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].maker", is("Toyota")))
            .andExpect(jsonPath("$[1].maker", is("Honda")));
    }

    @Test
    void whenGetCarById_thenReturnCar() throws Exception {
        Car car = new Car("Ford", "Fiesta");
        when(carService.getCarDetails(1L)).thenReturn(Optional.of(car));

        mockMvc.perform(get("/api/cars/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.maker", is("Ford")))
            .andExpect(jsonPath("$.model", is("Fiesta")));
    }

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car car = new Car("BMW", "X5");
        when(carService.save(Mockito.any())).thenReturn(car);

        String json = objectMapper.writeValueAsString(car);

        mockMvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.maker", is("BMW")))
            .andExpect(jsonPath("$.model", is("X5")));
    }
}
