package com.example.lab6_4;

import com.example.lab6_4.model.Car;
import com.example.lab6_4.service.CarManagerService;
import com.example.lab6_4.controller.CarController;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.hamcrest.Matchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(CarController.class)
public class CarControllerRestAssuredTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService carService;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void whenGetAllCars_thenReturnList() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        List<Car> cars = Arrays.asList(car1, car2);

        when(carService.getAllCars()).thenReturn(cars);

        RestAssuredMockMvc
                .given()
                .when()
                .get("/api/cars")
                .then()
                .statusCode(200)
                .body("$.size()", is(2))
                .body("[0].maker", is("Toyota"))
                .body("[1].model", is("Civic"));
    }

    @Test
    void whenGetCarById_thenReturnCorrectCar() {
        Car car = new Car("Mazda", "CX-5");
        car.setCarId(1L);

        when(carService.getCarDetails(1L)).thenReturn(java.util.Optional.of(car));

        RestAssuredMockMvc
                .given()
                .when()
                .get("/api/cars/1")
                .then()
                .statusCode(200)
                .body("maker", is("Mazda"))
                .body("model", is("CX-5"));
    }

    @Test
    void whenPostCar_thenCreateCar() {
        Car car = new Car("Ford", "Focus");
        when(carService.save(any(Car.class))).thenReturn(car);

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body("{ \"maker\": \"Ford\", \"model\": \"Focus\" }")
                .when()
                .post("/api/cars")
                .then()
                .statusCode(201)
                .body("maker", is("Ford"))
                .body("model", is("Focus"));
    }


}
