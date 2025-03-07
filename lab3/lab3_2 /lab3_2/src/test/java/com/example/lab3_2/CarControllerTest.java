package main.test;


import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import tqsdemo.carmngr.model.Car;
import tqsdemo.carmngr.service.CarManagerService;
import tqsdemo.carmngr.utils.JsonUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService carService;

    @Test
    void whenGetAllCars_thenReturnJsonArray() throws Exception {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        List<Car> allCars = Arrays.asList(car1, car2);

        when(carService.getAllCars()).thenReturn(allCars);

        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].maker", is("Toyota")))
            .andExpect(jsonPath("$[1].maker", is("Honda")));

        verify(carService, times(1)).getAllCars();
    }

    @Test
    void whenGetCarById_thenReturnCar() throws Exception {
        Car car = new Car("Ford", "Fiesta");
        when(carService.getCarDetails(1L)).thenReturn(Optional.of(car));

        mvc.perform(get("/api/cars/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.maker", is("Ford")))
            .andExpect(jsonPath("$.model", is("Fiesta")));

        verify(carService, times(1)).getCarDetails(1L);
    }

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car car = new Car("BMW", "X5");
        when(carService.save(Mockito.any())).thenReturn(car);

        mvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(car)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.maker", is("BMW")))
            .andExpect(jsonPath("$.model", is("X5")));

        verify(carService, times(1)).save(Mockito.any());
    }
}